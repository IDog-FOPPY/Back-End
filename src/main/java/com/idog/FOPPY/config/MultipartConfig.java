package com.idog.FOPPY.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;


@Configuration
public class MultipartConfig {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(512000000)); // 최대 파일 크기 설정
        factory.setMaxRequestSize(DataSize.ofBytes(512000000)); // 최대 요청 크기 설정
        return factory.createMultipartConfig();
    }
}