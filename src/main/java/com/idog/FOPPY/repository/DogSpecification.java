package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.Dog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DogSpecification {
    public static Specification<Dog> missingAndAreaAndDateAndBreed(
            String missingGu, String missingDong, LocalDate startDate, LocalDate endDate, Breed breed) {
        return new Specification<Dog>() {
            @Override
            public Predicate toPredicate(Root<Dog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(cb.equal(root.get("isMissing"), true));

                if (missingGu != null) {
                    predicates.add(cb.equal(root.get("missingGu"), missingGu));
                }

                if (missingDong != null) {
                    predicates.add(cb.equal(root.get("missingDong"), missingDong));
                }
                if (startDate != null && endDate != null) {
                    predicates.add(cb.between(root.get("missDate"), startDate, endDate));
                }

                if (breed != null) {
                    predicates.add(cb.equal(root.get("breed").as(String.class), breed.name()));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
