package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.Dog.DogInfoRequest;
import com.idog.FOPPY.dto.Dog.MissingInfoRequest;
import com.idog.FOPPY.dto.ResponseDTO;
import com.idog.FOPPY.dto.User.AddUserRequest;
import com.idog.FOPPY.service.DogService;
import com.idog.FOPPY.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
