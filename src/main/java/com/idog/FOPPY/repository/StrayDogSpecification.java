package com.idog.FOPPY.repository;

import com.idog.FOPPY.domain.Breed;
import com.idog.FOPPY.domain.StrayDog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StrayDogSpecification {
    public static Specification<StrayDog> AreaAndDateAndBreed(
            String missingGu, String missingDong, LocalDate startDate, LocalDate endDate, Breed breed) {
        return new Specification<StrayDog>() {
            @Override
            public Predicate toPredicate(Root<StrayDog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

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
