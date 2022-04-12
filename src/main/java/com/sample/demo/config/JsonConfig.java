package com.sample.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JsonConfig {

    @Bean("objectMapper")
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()

                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS) //매핑되는 필드가 없어도 예외 무시
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 시간 타입 오브젝트를 비활성화하고
                .modules(new JavaTimeModule()) // ISO8601 형식 타임형식을 사용
                .build();
    }
}