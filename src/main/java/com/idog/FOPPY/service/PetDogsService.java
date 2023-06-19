package com.idog.FOPPY.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.Member;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.repository.MemberRepository;
import com.idog.FOPPY.repository.PetDogsRepository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetDogsService {

    private final PetDogsRepository petDogsRepository;
    private final MemberRepository memberRepository;

    // AWS S3
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName; // replace with your bucket name

    @Autowired
    public PetDogsService(PetDogsRepository petDogsRepository, MemberRepository memberRepository) {
        this.petDogsRepository = petDogsRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 반려견 등록
     */
    @Transactional
    public List<Long> save(final Long uid, final PetRequestDTO params) {

        Member member = memberRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("해당 uid를 가진 멤버가 없습니다: " + uid));

        PetDogs petDogs = petDogsRepository.save(params.toEntity());

        member.addPet(petDogs.getPetId());
        petDogs.setMember(member);

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

        PetDogs petDogs = petDogsRepository.findById(petId)
                                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려견 정보입니다."));
        Long uid = petDogs.getMember().getUid();

        Member member = memberRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("해당 uid를 가진 멤버가 없습니다: " + uid));
        member.removePet(petId);

        petDogsRepository.deleteById(petId);
    }


}



