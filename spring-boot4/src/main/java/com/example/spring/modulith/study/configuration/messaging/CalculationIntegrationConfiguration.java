package com.example.spring.modulith.study.configuration.messaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;

/**
 * Messaging infrastructure for the calculation module.
 *
 * <p>Channels are registered in one batch by {@link CalculationChannelRegistrar}; they use
 * {@code DirectChannel}, so handlers stay on the caller thread and keep transaction semantics.
 * {@code spring.integration.channels.auto-create} is disabled in {@code application.yaml} to
 * make the registered set explicit.</p>
 */
@Configuration
@Import(CalculationChannelRegistrar.class)
@IntegrationComponentScan("com.example.spring.modulith.study.calculation")
class CalculationIntegrationConfiguration {
}
