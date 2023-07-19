package com.idog.FOPPY.controller;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.dto.Dog.DogInfoRequest;
import com.idog.FOPPY.dto.Dog.MissingDogResponse;
import com.idog.FOPPY.dto.Dog.MissingInfoRequest;
import com.idog.FOPPY.dto.ResponseDTO;
import com.idog.FOPPY.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "dog", description = "개 API")
@RequestMapping("/api/dog")
@RequiredArgsConstructor
@Controller
public class DogController {

    private final DogService dogService;
    @PostMapping("/create")
    @Operation(summary = "반려견 등록")
    public ResponseEntity<ResponseDTO<Long>> register(@RequestPart DogInfoRequest request,
                                                      @RequestPart("file") List<MultipartFile> multipartFile) {
        Long savedId = dogService.save(request, multipartFile);

        ResponseDTO<Long> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Dog save successful.");
        response.setData(savedId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/miss/{dogId}")
    @Operation(summary = "반려견 실종 처리")
    public ResponseEntity<ResponseDTO<Long>> setMissing(@PathVariable Long dogId,
                                                        @RequestBody MissingInfoRequest request) {
        Long setDogId = dogService.setMissing(dogId, request);

        ResponseDTO<Long> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Dog set Missing successful.");
        response.setData(setDogId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/missing")
    @Operation(summary = "조건에 따른 실종된 강아지 조회")
    public ResponseEntity<ResponseDTO<List<MissingDogResponse>>> getMissingDogs(
            @RequestParam(required = false) String missingGu,
            @RequestParam(required = false) String missingDong,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Breed breed
    ) {
        List<MissingDogResponse> missingDogs = dogService.getMissingDogs(missingGu, missingDong, startDate, endDate, breed);

        ResponseDTO<List<MissingDogResponse>> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Missing dogs fetched successfully.");
        response.setData(missingDogs);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
