package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.breedState;
import com.idog.FOPPY.service.PetDogsService;
import com.idog.FOPPY.service.StrayDogsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    @GetMapping("/StrayDogs/ByBreed/{petBreed}")
    public List<PetResponseDTO> findByBreed(@PathVariable(name = "견종") final breedState petBreed){
        return strayDogsService.findByBreed(petBreed);
    }

    @Operation(summary = "지역별 조회")
    @GetMapping("/StrayDogs/ByLocation/{missGu}")
    public List<PetResponseDTO> findByLocation(@PathVariable(name = "실종 지역(구)") final String missGu){
        return strayDogsService.findByLocation(missGu);
    }

    @Operation(summary = "날짜별 조회")
    @GetMapping("/StrayDogs/ByDate/{missDate}")
    public List<PetResponseDTO> findByDate(@PathVariable(name = "실종 날짜") final LocalDate date){
        return strayDogsService.findByDate(date);
    }
}