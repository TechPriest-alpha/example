package com.example.spring.modulith.study.calculation;

import java.util.Optional;

import com.example.spring.modulith.study.calculation.dto.CalculationCompleted;
import com.example.spring.modulith.study.calculation.dto.CalculationRequested;
import com.example.spring.modulith.study.configuration.messaging.CalculationChannels;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * Uniform send/receive facade for the calculation module: both operations go through
 * messaging channels and return their result directly.
 */
@MessagingGateway
public interface CalculationGateway {

    @Gateway(requestChannel = CalculationChannels.REQUESTS)
    CalculationCompleted calculate(CalculationRequested request);

    @Gateway(requestChannel = CalculationChannels.QUERIES)
    Optional<CalculationCompleted> find(long resultId);
}
