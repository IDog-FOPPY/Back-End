package com.idog.FOPPY.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.breedState;
import com.idog.FOPPY.service.PetDogsService;
import com.idog.FOPPY.service.StrayDogsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "유기견 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StrayDogsController {

    private final StrayDogsService strayDogsService;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

//    @Operation(summary = "전체 유기견 리스트 조회")
//    @GetMapping("/StrayDogs")
//    public List<PetResponseDTO> findAllStray() {
//        return strayDogsService.findAllStray();
//    }

    @Operation(summary = "견종, 지역, 날짜 조회")
    @GetMapping("/StrayDogs")
    public List<PetResponseDTO> findByParameters(@RequestParam(required = false) breedState petBreed,
                                                 @RequestParam(required = false) String missGu,
                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate missDate) {
        if (petBreed != null && missGu != null && missDate != null) {
            return strayDogsService.findByDateLocationBreed(missDate, missGu, petBreed);
        } else if (petBreed != null && missGu != null) {
            return strayDogsService.findByLocationBreed(missGu, petBreed);
        } else if (petBreed != null && missDate != null) {
            return strayDogsService.findByDateBreed(missDate, petBreed);
        } else if (missGu != null && missDate != null) {
            return strayDogsService.findByDateLocation(missDate, missGu);
        } else if (petBreed != null) {
            return strayDogsService.findByBreed(petBreed);
        } else if (missGu != null) {
            return strayDogsService.findByLocation(missGu);
        } else if (missDate != null) {
            return strayDogsService.findByDate(missDate);
        } else {
            return strayDogsService.findAllStray();
        }
    }

    @Operation(summary = "견종별 조회")
    @GetMapping("/StrayDogs/ByBreed/{petBreed}")
    public List<PetResponseDTO> findByBreed(@PathVariable(name = "petBreed") final breedState petBreed){
        return strayDogsService.findByBreed(petBreed);
    }

    @Operation(summary = "지역별 조회")
    @GetMapping("/StrayDogs/ByLocation/{missGu}")
    public List<PetResponseDTO> findByLocation(@PathVariable(name = "missGu") final String missGu){
        return strayDogsService.findByLocation(missGu);
    }

    @Operation(summary = "날짜별 조회")
    @GetMapping("/StrayDogs/ByDate/{missDate}")
    public List<PetResponseDTO> findByDate(@PathVariable(name = "missDate") final LocalDate date){
        return strayDogsService.findByDate(date);
    }

    @Operation(summary = "날짜+지역 조회")
    @GetMapping("/StrayDogs/ByDateLocation/{missDate},{missGu}")
    public List<PetResponseDTO> findByDateLocation(@PathVariable(name = "missDate") final LocalDate date,
                                                   @PathVariable(name = "missGu") final String missGu){
        return strayDogsService.findByDateLocation(date, missGu);
    }

    @Operation(summary = "날짜+견종 조회")
    @GetMapping("/StrayDogs/ByDatePetBreed/{missDate},{petBreed}")
    public List<PetResponseDTO> findByDatePetBreed(@PathVariable(name = "missDate") final LocalDate date,
                                                   @PathVariable(name = "petBreed") final breedState petBreed){
        return strayDogsService.findByDateBreed(date, petBreed);
    }

    @Operation(summary = "지역+견종 조회")
    @GetMapping("/StrayDogs/ByLocationPetBreed/{missGu},{petBreed}")
    public List<PetResponseDTO> findByDateLocation(@PathVariable(name = "missGu") final String missGu,
                                                   @PathVariable(name = "petBreed") final breedState petBreed){
        return strayDogsService.findByLocationBreed(missGu,petBreed);
    }

    @Operation(summary = "지역+견종+날짜 조회")
    @GetMapping("/StrayDogs/ByDateLocationPetBreed/{missDate},{missGu},{petBreed}")
    public List<PetResponseDTO> findByDateLocationBreed(@PathVariable(name = "missDate") final LocalDate date,
                                                        @PathVariable(name = "missGu") final String missGu,
                                                   @PathVariable(name = "petBreed") final breedState petBreed){
        return strayDogsService.findByDateLocationBreed(date, missGu, petBreed);
    }


//    @Operation(summary = "강아지 사진 등록")
//    @PostMapping("/StrayDogs/uploadFile}")
//    public ResponseEntity<String> uploadFile(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("dogUid") String dogUid // Add dogUid as a request parameter
//    ) {
//        try {
//            String fileExtension = getFileExtension(file.getOriginalFilename());
//
//            // Use the dog's UID as the fileName
//            String fileName = "/dog" + dogUid + "." + fileExtension;
//
//            String fileUrl = "https://" + bucket + fileName;
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            metadata.setContentLength(file.getSize());
//            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
//            return ResponseEntity.ok(fileUrl);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PostMapping("/StrayDogs/noseIdent")
    public ResponseEntity<String> noseIdent(
            @RequestParam("file") MultipartFile file,
            @RequestParam("dogUid") String dogUid
    ) {
        try {
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = "/dog" + dogUid + "." + fileExtension;
            String fileUrl = "https://" + bucket + fileName;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

            // Save the file temporarily
            Path tempPath = Files.createTempFile("temp", fileExtension);
            file.transferTo(tempPath);

            // Prepare the request to noseDetect API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(tempPath.toFile()));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity("http://18.212.193.216:8000/dogIdent", requestEntity, String.class);

            // Delete the temporary file
            Files.delete(tempPath);

            // Return the response from noseDetect API
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Utility method to extract the file extension from a filename
    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex < 0 || dotIndex >= filename.length() - 1) {
            return "";
        }
        return filename.substring(dotIndex + 1).toLowerCase();
    }



}
