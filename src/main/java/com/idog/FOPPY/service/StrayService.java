package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.StrayDog;
import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.dto.dog.DogInfoRequest;
import com.idog.FOPPY.dto.dog.MissingDogResponse;
import com.idog.FOPPY.dto.straydog.StrayDogResponse;
import com.idog.FOPPY.dto.straydog.StrayInfoRequest;
import com.idog.FOPPY.repository.DogSpecification;
import com.idog.FOPPY.repository.StrayDogRepository;
import com.idog.FOPPY.repository.StrayDogSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StrayService {
    private final S3Service s3Service;
    private final StrayDogRepository strayDogRepository;

    @Transactional
    public Long save(StrayInfoRequest request, List<MultipartFile> multipartFile) {
        List<String> imgUrlList = s3Service.upload(multipartFile, "/dog");

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
    public List<StrayDogResponse> sort(String missingGu, String missingDong, LocalDate startDate, LocalDate endDate, Breed breed) {
        List<StrayDog> strayDogs = strayDogRepository.findAll(StrayDogSpecification.AreaAndDateAndBreed(
                missingGu, missingDong, startDate, endDate, breed));

        return strayDogs.stream()
                .map(StrayDogResponse::of)
                .collect(Collectors.toList());
    }
}
