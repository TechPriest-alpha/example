package com.example.spring.modulith.study.configuration;

import com.example.spring.modulith.study.calculation.CalculationCompleted;
import com.example.spring.modulith.study.calculation.CalculationRequested;
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

        /** Logs the incoming domain command as it is published. */
        @EventListener
        void onRequested(CalculationRequested event) {
            log.info("Domain event published: CalculationRequested(date={}, numbers={})",
                event.date(), event.numbers());
        }

        /** Logs the processing outcome before the controller builds the HTTP response. */
        @EventListener
        void onCompleted(CalculationCompleted event) {
            log.info("Domain event published: CalculationCompleted(id={}, sum={})", event.id(), event.sum());
        }
    }
}
