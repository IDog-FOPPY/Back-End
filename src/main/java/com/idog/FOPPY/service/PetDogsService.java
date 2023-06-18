package com.idog.FOPPY.service;

import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.Member;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.repository.MemberRepository;
import com.idog.FOPPY.repository.PetDogsRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetDogsService {

    @Autowired
    private final PetDogsRepository petDogsRepository;
    @Autowired
    private final MemberRepository memberRepository;

    /**
     * 반려견 등록
     */
    @Transactional
    public List<Long> save(final Long uid, final PetRequestDTO params) {

        Member member = memberRepository.findById(uid).orElse(null);
        PetDogs petDogs = petDogsRepository.save(params.toEntity());

        member.addPet(petDogs.getPetId());

        return member.getPetIds();
    }

    /**
     * 전체 반려견 조회
     */
    public List<PetResponseDTO> findAll() {
        List<PetDogs> list = petDogsRepository.findAll();
        return list.stream().map(PetResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * ID로 반려견 상세정보 조회
     */
    @Transactional
    public PetResponseDTO findById(final Long petId) {
        PetDogs petDogs = petDogsRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려견 정보입니다."));
        return new PetResponseDTO(petDogs);
    }

    /**
     * 반려견 정보 수정
     */
    @Transactional
    public Long update(Long petId, PetRequestDTO params) {
        PetDogs petDogs = petDogsRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려견 정보입니다."));

        petDogs.updatePetDogs(params.getPetName(), params.getPetSex(), params.getPetBreed(),
                params.getPetOld(), params.getDisease(), params.getNeutered(), params.getMissDetail(), params.getMissed(),
                params.getMissCity(), params.getMissGu(), params.getMissDong(), params.getMissDetail(),
                params.getMissDate(), params.getMissTime(), params.getEtc());

        return petId;
    }


    /**
     * 반려견 정보 삭제
     */
    @Transactional
    public void deleteById(Long petId){
        petDogsRepository.deleteById(petId);
    }

}



