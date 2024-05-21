package com.example.demo.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectConfiguration {

    // For auth when consuming rest services with Open Feign
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("admin@eazyschool.com", "admin123");
    }

    // Rest template
    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder.basicAuthentication("admin@eazyschool.com", "admin123").build();
    }
}
