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

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StrayService {
    private final S3Service s3Service;
    private final StrayDogRepository strayDogRepository;
    private final DogRepository dogRepository;

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
        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);


        multipartBodyBuilder.addFormDataPart("file", multipartFile.getOriginalFilename(),
                RequestBody.create(multipartFile.getBytes(), MediaType.parse(multipartFile.getContentType())));

        Request request = new Request.Builder()
                .url("http://3.39.235.118:8000/dogIdentification")
                .post(multipartBodyBuilder.build())
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        JsonNode responseJson = new ObjectMapper().readTree(responseBody);
        System.out.println(responseJson.toString());

// Check the status of the response
        if (responseJson.has("status")) {
            int status = responseJson.get("status").asInt();

            // Only process the data if the status is 200
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
                            responsesMap.putIfAbsent(dog.getId(), findStrayResponse);
                        }
                    }
                }

                List<FindStrayResponse> responses = new ArrayList<>(responsesMap.values());

                Collections.sort(responses, new Comparator<FindStrayResponse>() {
                    @Override
                    public int compare(FindStrayResponse o1, FindStrayResponse o2) {
                        return Double.compare(o2.getSimilarity(), o1.getSimilarity());
                    }
                });

                return responses;

            }
            else {
                // If the status is not 200, return the status code in the data
                throw new Exception(String.valueOf(status));
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
