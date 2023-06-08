package com.idog.FOPPY.repository;

import com.idog.FOPPY.entity.PetDogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetDogsRepository extends JpaRepository<PetDogs, Long> {

}
