package com.idog.FOPPY.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.service.PetDogsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "반려견 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class PetDogsController {
    private final PetDogsService petDogsService;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Operation(summary = "반려견 등록")
    @PostMapping("/PetDogs/save/{uid}")
    public List<Long> save(@PathVariable final Long uid, @RequestBody final PetRequestDTO params) {
        return petDogsService.save(uid, params);
    }

    @Operation(summary = "반려견 이미지 등록")
    @PostMapping("/PetDogs/saveImg")
    public ResponseEntity<String> saveImg(
            @RequestParam("memberUid") String memberId,
            @RequestParam("files") MultipartFile[] files
    ) {
        try {
            if(files.length != 3){
                return new ResponseEntity<>("Please provide exactly 3 images", HttpStatus.BAD_REQUEST);
            }

            List<String> fileUrls;
            fileUrls = new ArrayList<>();

            for (int i = 0; i < files.length; i++) {
                String fileName = "dog_" + memberId + "_" + (i+1);
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(files[i].getContentType());
                objectMetadata.setContentLength(files[i].getSize());

                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, files[i].getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                String url = amazonS3Client.getResourceUrl(bucket, fileName);
                fileUrls.add(url);
            }

            return new ResponseEntity<>("File uploaded : " + fileUrls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "반려견 이미지 가져오기")
    @GetMapping("/PetDogs/getImages/{memberUid}")
    public ResponseEntity<List<String>> getImages(@PathVariable("memberUid") String memberId) {
        try {
            ListObjectsV2Result result = amazonS3Client.listObjectsV2(bucket);
            List<S3ObjectSummary> objects = result.getObjectSummaries();

            List<String> fileUrls = objects.stream()
                    .filter(o -> o.getKey().startsWith("dog_" + memberId))
                    .map(o -> amazonS3Client.getUrl(bucket, o.getKey()).toString())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(fileUrls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "전체 반려견 리스트 조회")
    @GetMapping("/PetDogs/get")
    public List<PetResponseDTO> findAll() {
        return petDogsService.findAll();
    }


    @Operation(summary = "반려견 상세정보 조회")
    @GetMapping("/PetDogs/get/{petId}")
    public PetResponseDTO findById(@PathVariable(name = "petId") final Long petId){
        return petDogsService.findById(petId);
    }


    @Operation(summary = "반려견 정보 수정")
    @PatchMapping("/PetDogs/update/{petId}")
    public Long update(@PathVariable(name = "petId") final Long petId,@RequestBody final PetRequestDTO params) {
        return petDogsService.update(petId, params);
    }


    @Operation(summary = "반려견 정보 삭제")
    @DeleteMapping("/PetDogs/delete/{petId}")
    public void delete(@PathVariable(name = "petId") final Long petId){
        petDogsService.deleteById(petId);
    }



}

