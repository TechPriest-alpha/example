package com.example.spring.modulith.study.api;

import java.util.List;

import com.example.spring.generated.study.model.CalculationRequest;
import com.example.spring.generated.study.model.CalculationResponse;
import com.example.spring.generated.study.model.NumberStringResponse;
import com.example.spring.modulith.study.api.generated.StudyApi;
import com.example.spring.modulith.study.calculation.CalculationGateway;
import com.example.spring.modulith.study.calculation.dto.CalculationCompleted;
import com.example.spring.modulith.study.calculation.dto.CalculationRequested;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
class StudyController implements StudyApi {

    private final CalculationGateway calculations;

    @Override
    public ResponseEntity<List<CalculationResponse>> calculate(CalculationRequest request) {
        log.info("Received calculation request for date {} with numbers {}", request.getDate(), request.getNumbers());

        var result = calculations.calculate(CalculationRequested.from(request));
        log.info("Returning calculation result id={} sum={}", result.id(), result.sum());
        return ResponseEntity.ok(List.of(new CalculationResponse(result.id(), result.sum())));
    }

    @Override
    public ResponseEntity<NumberStringResponse> getNumberString(Long id) {
        log.debug("Looking up calculation result {}", id);
        return calculations.find(id)
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
