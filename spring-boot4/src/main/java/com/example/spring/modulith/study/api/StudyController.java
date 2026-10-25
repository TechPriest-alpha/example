package com.example.spring.modulith.study.api;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.example.spring.generated.study.model.CalculationRequest;
import com.example.spring.generated.study.model.CalculationResponse;
import com.example.spring.generated.study.model.NumberStringResponse;
import com.example.spring.modulith.study.api.generated.StudyApi;
import com.example.spring.modulith.study.calculation.dto.CalculationCompleted;
import com.example.spring.modulith.study.calculation.dto.CalculationRequested;
import com.example.spring.modulith.study.calculation.CalculationQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
class StudyController implements StudyApi {

    private final ApplicationEventPublisher events;
    private final CalculationQueryService queries;

    @Override
    public ResponseEntity<List<CalculationResponse>> calculate(CalculationRequest request) {
        log.info("Received calculation request for date {} with numbers {}", request.getDate(), request.getNumbers());

        var completed = new AtomicReference<CalculationCompleted>();
        events.publishEvent(CalculationRequested.from(request, completed::set));

        var result = completed.get();
        log.info("Returning calculation result id={} sum={}", result.id(), result.sum());
        return ResponseEntity.ok(List.of(new CalculationResponse(result.id(), result.sum())));
    }

    @Override
    public ResponseEntity<NumberStringResponse> getNumberString(Long id) {
        log.debug("Looking up calculation result {}", id);
        return queries.find(id)
            .map(StudyController::toResponse)
            .orElseGet(() -> notFound(id));
    }

    private static ResponseEntity<NumberStringResponse> toResponse(CalculationCompleted result) {
        return ResponseEntity.ok(new NumberStringResponse(result.id(), result.sum()));
    }

    private static ResponseEntity<NumberStringResponse> notFound(Long id) {
        log.debug("Calculation result {} not found", id);
        return ResponseEntity.notFound().build();
    }
}
