package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.service.PetDogsService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Tag(name = "반려견 API")
@RestController
@RequestMapping("/api/PetDogs")
@RequiredArgsConstructor
class PetDogsController {
    private final PetDogsService petDogsService;


    @Operation(summary = "반려견 등록")
    @PostMapping("/post")
    public Long save(@PathVariable(name = "uid") final Long uid, @RequestBody final PetRequestDTO params) {
        return petDogsService.save(uid,params);
    }


    @Operation(summary = "전체 반려견 리스트 조회")
    @GetMapping("/get")
    public List<PetResponseDTO> findAll() {
        return petDogsService.findAll();
    }


    @Operation(summary = "반려견 상세정보 조회")
    @GetMapping("/getDetail/{petId}")
    public PetResponseDTO findById(@PathVariable(name = "petId") final Long petId){
        return petDogsService.findById(petId);
    }


    @Operation(summary = "반려견 정보 수정")
    @PatchMapping("/update/{petId}")
    public Long update(@PathVariable(name = "petId") final Long petId,@RequestBody final PetRequestDTO params) {
        return petDogsService.update(petId, params);
    }


    @Operation(summary = "반려견 정보 삭제")
    @DeleteMapping("/delete/{petId}")
    public void delete(@PathVariable(name = "petId") final Long petId){
        petDogsService.deleteById(petId);
    }

}

