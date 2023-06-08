package com.idog.FOPPY.repository;

import com.idog.FOPPY.entity.StrayDogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrayDogsRepository extends JpaRepository<StrayDogs, Long> {
}
