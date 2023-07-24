package com.idog.FOPPY.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissingTime {
    @Column(nullable = true)
    private String hour;
    @Column(nullable = true)
    private String minute;
}
