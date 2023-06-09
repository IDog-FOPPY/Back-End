package com.idog.FOPPY.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    USERNAME_DUPLICATED(HttpStatus.CONFLICT, "Username is duplicated"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "Username is not found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is not matched");

    private HttpStatus httpStatus;
    private String message;
}
