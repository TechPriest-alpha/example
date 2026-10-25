package com.example.spring.modulith.study.calculation.internal;

import com.example.spring.modulith.study.calculation.dto.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class DomainEventLogger {

    /** Logs every domain event as-is once it is published. */
    @EventListener
    void on(DomainEvent event) {
        log.info("Domain event published: {}", event);
    }
}
