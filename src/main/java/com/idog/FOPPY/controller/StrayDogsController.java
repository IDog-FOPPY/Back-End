package com.idog.FOPPY.controller;


import com.idog.FOPPY.dto.stray.StrayResponseDTO;
import com.idog.FOPPY.service.StrayDogsServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유기견 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StrayDogsController {

    private final StrayDogsServices strayDogsServices;

    @Operation(summary = "전체 유기견 리스트 조회")
    @GetMapping("/StrayDogs")
    public List<StrayResponseDTO> findAll() {
        return strayDogsServices.findAll();
    }


    @Operation(summary = "유기견 상세정보 조회")
    @GetMapping("/StrayDogs/{strayId}")
    public StrayResponseDTO findById(@PathVariable final Long strayId){
        return strayDogsServices.findById(strayId);
    }


    @Operation(summary = "유기견 정보 삭제")
    @DeleteMapping("/StrayDogs/{strayId}")
    public void delete(@PathVariable final Long strayId){
        strayDogsServices.deleteById(strayId);
    }
}
