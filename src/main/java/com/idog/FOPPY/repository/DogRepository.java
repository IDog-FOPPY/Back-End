package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.Dog;
import com.idog.FOPPY.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long>, JpaSpecificationExecutor<Dog> {

    List<Dog> findByUser(User user);

}

