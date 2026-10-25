package com.example.spring.modulith.study.configuration;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;

@Configuration
class JacksonConfiguration {

    @Bean
    JsonMapperBuilderCustomizer jsonMapperBuilderCustomizer() {
        return builder -> builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
