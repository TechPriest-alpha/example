package com.example.spring.modulith.study.calculation.internal;

import com.example.spring.modulith.study.calculation.dto.CalculationCompleted;
import com.example.spring.modulith.study.calculation.dto.CalculationRequested;
import com.example.spring.modulith.study.configuration.messaging.CalculationChannels;
import com.example.spring.modulith.study.data.dto.CalculationRequestRecord;
import com.example.spring.modulith.study.data.dto.CalculationResultRecord;
import com.example.spring.modulith.study.data.repositories.CalculationRequestRepository;
import com.example.spring.modulith.study.data.repositories.CalculationResultRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class CalculationHandler {

    private final CalculationRequestRepository requests;
    private final CalculationResultRepository results;
    private final ApplicationEventPublisher events;

    @ServiceActivator(inputChannel = CalculationChannels.REQUESTS)
    @Transactional(timeoutString = "${app.calculation.transaction.write-timeout-seconds}")
    public CalculationCompleted handle(@Valid CalculationRequested request) {
        log.debug(
            "Handling calculation request for date {} with {} numbers",
            request.request().getDate(),
            request.request().getNumbers().size()
        );

        var savedRequest = requests.save(CalculationRequestRecord.of(request.request()));
        var result = results.save(CalculationResultRecord.of(savedRequest, request.total()));

        var completed = CalculationCompleted.from(result);
        events.publishEvent(completed);
        return completed;
    }
}
