package com.example.spring.modulith.study.calculation;

import com.example.spring.modulith.study.calculation.dto.CalculationCompleted;
import com.example.spring.modulith.study.calculation.dto.CalculationRequested;
import com.example.spring.modulith.study.data.dto.CalculationRequestRecord;
import com.example.spring.modulith.study.data.dto.CalculationResultRecord;
import com.example.spring.modulith.study.data.repositories.CalculationRequestRepository;
import com.example.spring.modulith.study.data.repositories.CalculationResultRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
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

    @EventListener
    @Transactional(timeoutString = "${app.calculation.transaction.write-timeout-seconds}")
    public void handle(@Valid CalculationRequested event) {
        log.debug("Handling calculation request for date {} with {} numbers",
            event.request().getDate(), event.request().getNumbers().size());

        var request = requests.save(CalculationRequestRecord.of(event.request()));
        var result = results.save(CalculationResultRecord.of(request, event.total()));

        var completed = CalculationCompleted.from(result);
        events.publishEvent(completed);
        event.onCompleted().accept(completed);
    }
}
