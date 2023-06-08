package com.idog.FOPPY.service;

import com.idog.FOPPY.dto.stray.StrayRequestDTO;
import com.idog.FOPPY.dto.stray.StrayResponseDTO;
import com.idog.FOPPY.entity.StrayDogs;
import com.idog.FOPPY.repository.StrayDogsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StrayDogsServices {

    @Autowired
    private final StrayDogsRepository strayDogsRepository;

    /**
     * 유기견 추가
     */
    @Transactional
    public Long save(final StrayRequestDTO params) {

        StrayDogs strayDogs = strayDogsRepository.save(params.toEntity());
        return strayDogs.getStrayId();
    }

    /**
     * 전체 유기견 조회
     */
    public List<StrayResponseDTO> findAll() {
        List<StrayDogs> list = strayDogsRepository.findAll();
        return list.stream().map(StrayResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * ID로 반려견 상세정보 조회
     */
    @Transactional
    public StrayResponseDTO findById(final Long strayId) {
        StrayDogs strayDogs = strayDogsRepository.findById(strayId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려견 정보입니다."));
        return new StrayResponseDTO(strayDogs);
    }

    /**
     * 반려견 정보 삭제
     */
    @Transactional
    public void deleteById(Long strayId){
        strayDogsRepository.deleteById(strayId);
    }

}
