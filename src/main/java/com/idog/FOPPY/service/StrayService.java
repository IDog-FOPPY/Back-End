package com.idog.FOPPY.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.StrayDog;
import com.idog.FOPPY.dto.straydog.FindStrayResponse;
import com.idog.FOPPY.dto.straydog.StrayDogResponse;
import com.idog.FOPPY.dto.straydog.StrayInfoRequest;
import com.idog.FOPPY.repository.DogRepository;
import com.idog.FOPPY.repository.StrayDogRepository;
import com.idog.FOPPY.repository.StrayDogSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StrayService {
    private final S3Service s3Service;
    private final StrayDogRepository strayDogRepository;
    private final DogRepository dogRepository;

    private WebClient webClient =
            WebClient
                    .builder()
                    .baseUrl("http://43.202.57.167:8000")
                    .build();

    @Transactional
    public Long save(StrayInfoRequest request, List<MultipartFile> multipartFile) {
        List<String> imgUrlList = s3Service.upload(multipartFile, "/stray");

        StrayDog strayDog = StrayDog.builder()
                .missingCity(request.getMissingCity())
                .missingGu(request.getMissingGu())
                .missingDong(request.getMissingDong())
                .missingDetailedLocation(request.getMissingDetailedLocation())
                .missDate(request.getMissDate())
                .missTime(request.getMissTime())
                .breed(request.getBreed())
                .etc(request.getEtc())
                .imgUrlList(imgUrlList)
                .build();

        StrayDog stray = strayDogRepository.save(strayDog);

        return stray.getId();
    }

    @Transactional
    public List<FindStrayResponse> findStray(MultipartFile multipartFile) throws Exception {

        ByteArrayResource fileResource = new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };

        Mono<String> responseMono = webClient.post()
                .uri("/dogIdentification")
                .contentType(MediaType.MULTIPART_FORM_DATA) // 추가된 contentType 설정
                .body(BodyInserters.fromMultipartData("file", fileResource)) // 수정된 부분
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError(), clientResponse -> Mono.error(new Exception("Client error")))
                .onStatus(httpStatus -> httpStatus.is5xxServerError(), clientResponse -> Mono.error(new Exception("Server error")))
                .bodyToMono(String.class)
                .timeout(Duration.ofMinutes(5));


        String responseBody = responseMono.block(Duration.ofMinutes(3));

        JsonNode responseJson = new ObjectMapper().readTree(responseBody);
        System.out.println(responseJson.toString());

        if (responseJson.has("status")) {
            int status = responseJson.get("status").asInt();

            if (status == 200) {
                JsonNode data = responseJson.get("data");
                Map<String, Double> filenameSimilarityMap = new HashMap<>();

                if (data.isArray()) {
                    for (JsonNode resultNode : data) {
                        if (resultNode.has("filename") && resultNode.has("similarity")) {
                            String filename = resultNode.get("filename").asText();
                            double similarity = resultNode.get("similarity").asDouble();
                            filenameSimilarityMap.put(filename, similarity);
                        }
                    }
                }

                Map<Long, FindStrayResponse> responsesMap = new HashMap<>();
                List<Dog> allDogs = dogRepository.findAll();
                for (Dog dog : allDogs) {
                    for (String filename : dog.getNoseImgUrlList()) {
                        if (filenameSimilarityMap.containsKey(filename)) {
                            FindStrayResponse findStrayResponse = new FindStrayResponse(filenameSimilarityMap.get(filename), dog);
                            findStrayResponse.setCode(200);
                            responsesMap.putIfAbsent(dog.getId(), findStrayResponse);
                        }
                    }
                }

                List<FindStrayResponse> responses = new ArrayList<>(responsesMap.values());
                responses.sort((o1, o2) -> Double.compare(o2.getSimilarity(), o1.getSimilarity()));

                return responses;

            } else {
                FindStrayResponse findStrayResponse = new FindStrayResponse();
                findStrayResponse.setCode(status);
                return Collections.singletonList(findStrayResponse);
            }
        } else {
            throw new RuntimeException("Invalid response: no status field");
        }
    }

    @Transactional
    public List<StrayDogResponse> sort(String missingGu, String missingDong, LocalDate startDate, LocalDate endDate, Breed breed) {
        List<StrayDog> strayDogs = strayDogRepository.findAll(StrayDogSpecification.AreaAndDateAndBreed(
                missingGu, missingDong, startDate, endDate, breed));

        return strayDogs.stream()
                .map(StrayDogResponse::of)
                .collect(Collectors.toList());
    }
}
