package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.service.PetDogsService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Tag(name = "반려견 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class PetDogsController {
    private final PetDogsService petDogsService;


    @Operation(summary = "반려견 등록")
    @PostMapping("/PetDogs")
    public Long save(@RequestBody final PetRequestDTO params) {
        return petDogsService.save(params);
    }


    @Operation(summary = "전체 반려견 리스트 조회")
    @GetMapping("/PetDogs")
    public List<PetResponseDTO> findAll() {
        return petDogsService.findAll();
    }


    @Operation(summary = "반려견 상세정보 조회")
    @GetMapping("/PetDogs/{petId}")
    public PetResponseDTO findById(@PathVariable final Long petId){
        return petDogsService.findById(petId);
    }


    @Operation(summary = "반려견 정보 수정")
    @PatchMapping("/PetDogs/{petId}")
    public Long save(@PathVariable final Long petId, @RequestBody final PetRequestDTO params) {
        return petDogsService.update(petId, params);
    }


    @Operation(summary = "반려견 정보 삭제")
    @DeleteMapping("/PetDogs/{petId}")
    public void delete(@PathVariable final Long petId){
        petDogsService.deleteById(petId);
    }

}

