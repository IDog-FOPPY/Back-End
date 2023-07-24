package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.StrayDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StrayDogRepository extends JpaRepository<StrayDog, Long>, JpaSpecificationExecutor<StrayDog> {
}
