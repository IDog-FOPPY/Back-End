package com.idog.FOPPY.dto.member;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Builder
public class JoinResponse {

    private Long id;
    private String username;
    private LocalDate createdAt;
}
