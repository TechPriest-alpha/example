package com.example.spring.modulith.study.calculation.internal;

import java.util.Optional;

import com.example.spring.modulith.study.calculation.dto.CalculationCompleted;
import com.example.spring.modulith.study.configuration.messaging.CalculationChannels;
import com.example.spring.modulith.study.data.repositories.CalculationResultRepository;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class CalculationQueryService {

    private final CalculationResultRepository results;

    @ServiceActivator(inputChannel = CalculationChannels.QUERIES)
    @Transactional(readOnly = true, timeoutString = "${app.calculation.transaction.read-timeout-seconds}")
    public Optional<CalculationCompleted> find(@Positive long resultId) {
        log.debug("Reading calculation result {}", resultId);
        return results.findById(resultId).map(CalculationCompleted::from);
    }
}
