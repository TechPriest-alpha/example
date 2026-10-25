package com.example.spring.modulith.study.configuration;

import com.example.spring.modulith.study.calculation.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
class EventConfiguration {

    /**
     * Spring Modulith already defines the {@code applicationEventMulticaster} bean, so event
     * interception is done with an ordinary listener instead of replacing the multicaster.
     */
    @Bean
    DomainEventLogger domainEventLogger() {
        return new DomainEventLogger();
    }

    @Slf4j
    static class DomainEventLogger {

        /** Logs every domain event as-is once it is published. */
        @EventListener
        void on(DomainEvent event) {
            log.info("Domain event published: {}", event);
        }
    }
}
