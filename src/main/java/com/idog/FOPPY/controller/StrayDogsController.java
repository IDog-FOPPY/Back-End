package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.breedState;
import com.idog.FOPPY.service.PetDogsService;
import com.idog.FOPPY.service.StrayDogsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "유기견 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StrayDogsController {

    private final StrayDogsService strayDogsService;

    @Operation(summary = "전체 유기견 리스트 조회")
    @GetMapping("/StrayDogs")
    public List<PetResponseDTO> findAllStray() {
        return strayDogsService.findAllStray();
    }

    @Operation(summary = "견종별 조회")
    @GetMapping("/StrayDogs/{petBreed}")
    public List<PetResponseDTO> findByBreed(@PathVariable(name = "petBreed")final breedState petBreed){
        return strayDogsService.findByBreed(petBreed);
    }
}
