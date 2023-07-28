package com.idog.FOPPY.controller;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.dto.dog.*;
import com.idog.FOPPY.dto.ResponseDTO;
import com.idog.FOPPY.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<ResponseDTO<Long>> register(@RequestPart DogCreateRequest request,
                                                      @RequestPart("file") List<MultipartFile> multipartFile) throws IOException, InterruptedException {
        Long savedId = dogService.save(request, multipartFile);

        ResponseDTO<Long> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Dog save successful.");
        response.setData(savedId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @PatchMapping("/{dogId}")
    @Operation(summary = "강아지 정보 수정")
    public ResponseEntity<ResponseDTO<Long>> update(@PathVariable Long dogId, @RequestBody DogInfoRequest request) {
        Long setDogId = dogService.update(dogId, request);

        ResponseDTO<Long> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Dog info change successful.");
        response.setData(setDogId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/missing")
    @Operation(summary = "조건에 따른 실종된 강아지 조회")
    public ResponseEntity<ResponseDTO<Page<MissingDogResponse>>> getMissingDogs(
            @RequestParam(required = false) String missingGu,
            @RequestParam(required = false) String missingDong,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Breed breed,
            Pageable pageable
    ) {
        Page<MissingDogResponse> missingDogs = dogService.getMissingDogs(missingGu, missingDong, startDate, endDate, breed, pageable);

        ResponseDTO<Page<MissingDogResponse>> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Missing dogs fetched successfully.");
        response.setData(missingDogs);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }


    @GetMapping("")
    @Operation(summary = "내 강아지 조회")
    public ResponseEntity<ResponseDTO<List<DogResponse>>> getMissingDogs() {
        List<DogResponse> myDogs = dogService.getMyDogs();
        ResponseDTO<List<DogResponse>> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("get my dogs successfully.");
        response.setData(myDogs);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{dogId}")
    @Operation(summary = "강아지 정보 조회")
    public ResponseEntity<ResponseDTO<DogInfoResponse>> getDetail(@PathVariable Long dogId) {
        DogInfoResponse dogInfoResponse = dogService.getDetail(dogId);
        ResponseDTO<DogInfoResponse> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("get my dogs successfully.");
        response.setData(dogInfoResponse);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping ("/{dogId}")
    @Operation(summary = "강아지 삭제")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long dogId) {
        dogService.delete(dogId);
        ResponseDTO<Void> response = new ResponseDTO<>();
        response.setStatus(true);
        response.setMessage("Dog deletion successful.");

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
