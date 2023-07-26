package com.idog.FOPPY.controller;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.dto.ResponseDTO;
import com.idog.FOPPY.dto.dog.DogInfoRequest;
import com.idog.FOPPY.dto.dog.MissingDogResponse;
import com.idog.FOPPY.dto.straydog.FindStrayResponse;
import com.idog.FOPPY.dto.straydog.StrayDogResponse;
import com.idog.FOPPY.dto.straydog.StrayInfoRequest;
import com.idog.FOPPY.service.DogService;
import com.idog.FOPPY.service.StrayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "strayDog", description = "유기견 API")
@RequestMapping("/api/stray")
@RequiredArgsConstructor
@Controller
public class StrayDogController {

    private final StrayService strayService;

    @PostMapping("/save")
    @Operation(summary = "유기견 등록")
    public ResponseEntity<ResponseDTO<Long>> register(@RequestPart StrayInfoRequest request,
                                                      @RequestPart("file") List<MultipartFile> multipartFile) {
        Long savedId = strayService.save(request, multipartFile);

        ResponseDTO<Long> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Stray dog save successful.");
        response.setData(savedId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("")
    @Operation(summary = "유기견 일치 비문 검색")
    public ResponseEntity<ResponseDTO<List<FindStrayResponse>>> register(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        List<FindStrayResponse> stray = strayService.findStray(multipartFile);

        ResponseDTO<List<FindStrayResponse>> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Stray dog find successful.");
        response.setData(stray);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("")
    @Operation(summary = "조건에 따른 유기견 강아지 조회")
    public ResponseEntity<ResponseDTO<List<StrayDogResponse>>> getMissingStrays(
            @RequestParam(required = false) String missingGu,
            @RequestParam(required = false) String missingDong,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Breed breed
    ) {
        List<StrayDogResponse> missingDogs = strayService.sort(missingGu, missingDong, startDate, endDate, breed);

        ResponseDTO<List<StrayDogResponse>> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("stray dogs fetched successfully.");
        response.setData(missingDogs);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
