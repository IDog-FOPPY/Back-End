package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.User.CreateAccessTokenRequest;
import com.idog.FOPPY.dto.User.CreateAccessTokenResponse;
import com.idog.FOPPY.service.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "jwt", description = "jwt API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        String newAccessToken = jwtService.createNewAccessToken(refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
