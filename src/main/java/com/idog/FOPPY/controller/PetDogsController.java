package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.service.PetDogsService;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class PetDogsController {
    private final PetDogsService petDogsService;


    /**
     * 반려견 생성
     */
    @PostMapping("/PetDogs")
    public Long save(@RequestBody final PetRequestDTO params) {
        return petDogsService.save(params);
    }

    /**
     * 반려견 정보 조회
     */
    @GetMapping("/PetDogs")
    public List<PetResponseDTO> findAll() {
        return petDogsService.findAll();
    }

    /**
     * 반려견 정보 수정
     */
    @PatchMapping("/PetDogs/{petId}")
    public Long save(@PathVariable final Long petId, @RequestBody final PetRequestDTO params) {
        return petDogsService.update(petId, params);
    }


}

