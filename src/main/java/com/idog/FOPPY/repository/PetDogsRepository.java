package com.idog.FOPPY.repository;

import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.entity.breedState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PetDogsRepository extends JpaRepository<PetDogs, Long> {

    List<PetDogs> findByMissedIsTrue();

    List<PetDogs> findByPetBreed(breedState petBreed);

    List<PetDogs> findByMissGu(String missGu);

    List<PetDogs> findByMissDate(LocalDate date);
}