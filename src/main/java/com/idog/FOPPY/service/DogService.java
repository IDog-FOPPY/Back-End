package com.idog.FOPPY.service;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.dto.Dog.MissingDogResponse;
import com.idog.FOPPY.dto.Dog.MissingInfoRequest;
import com.idog.FOPPY.dto.Dog.DogInfoRequest;
import com.idog.FOPPY.repository.DogRepository;
import com.idog.FOPPY.repository.DogSpecification;
import com.idog.FOPPY.repository.UserRepository;
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

public class DogService {

    private final UserRepository userRepository;
    private final DogRepository dogRepository;
    private final S3Service s3Service;

    @Transactional
    public Long save(DogInfoRequest dogInfoRequest, List<MultipartFile> multipartFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = (String)authentication.getPrincipal();

        User user = userRepository.findByEmail(userEmail)  // Assuming you have a findByUsername method
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userEmail));

        List<String> imgUrlList = s3Service.upload(multipartFile);

        Dog dog = user.addDog(dogInfoRequest.getName(), dogInfoRequest.getBirth(), dogInfoRequest.getSex(), dogInfoRequest.getBreed(),
                dogInfoRequest.getNote(), dogInfoRequest.getDisease(), imgUrlList);

        dogRepository.save(dog);

        return dog.getId();
    }

    public Long setMissing(Long id, MissingInfoRequest missingInfo) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dog ID"));

        dog.markAsMissing(missingInfo);

        dogRepository.save(dog);

        return dog.getId();
    }

    @Transactional
    public List<MissingDogResponse> getMissingDogs(String missingGu, String missingDong, LocalDate startDate, LocalDate endDate, Breed breed) {
        List<Dog> dogs = dogRepository.findAll(DogSpecification.missingAndAreaAndDateAndBreed(
                missingGu, missingDong, startDate, endDate, breed));

        return dogs.stream()
                .map(MissingDogResponse::of)
                .collect(Collectors.toList());
    }
}
