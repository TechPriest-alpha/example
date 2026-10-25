package com.example.spring.modulith.study.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class SecurityConfiguration {

    /**
     * Single security chain for the application.
     *
     * <ul>
     *   <li>CSRF is disabled because the API is stateless JSON (no browser sessions or cookies).</li>
     *   <li>Public (no authentication): {@code /api/**} business API, {@code /test/**} legacy sample
     *       endpoint, {@code /actuator/health} for probes, {@code /h2-console/**} for local DB access.</li>
     *   <li>Every other path requires authentication; HTTP Basic is the only configured mechanism.</li>
     *   <li>Frames are allowed from the same origin so the H2 console UI can render.</li>
     * </ul>
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
            // No server-side session state is created, so classic CSRF protection does not apply.
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Business API and sample endpoint: anonymous access is intentional.
                .requestMatchers("/api/**", "/test/**").permitAll()
                // Liveness/readiness probes must stay reachable without credentials.
                .requestMatchers("/actuator/health").permitAll()
                // Local H2 console only; remove this matcher if the console is disabled.
                .requestMatchers("/h2-console/**").permitAll()
                // Anything not matched above is authenticated (HTTP Basic below).
                .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            // The H2 console renders inside frames served from the same origin.
            .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
            .build();
    }
}
