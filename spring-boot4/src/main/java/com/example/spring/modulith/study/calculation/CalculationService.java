package com.example.spring.modulith.study.calculation;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Optional;

import com.example.spring.modulith.study.data.CalculationRequestRecord;
import com.example.spring.modulith.study.data.CalculationRequestRepository;
import com.example.spring.modulith.study.data.CalculationResultRecord;
import com.example.spring.modulith.study.data.CalculationResultRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class CalculationService {

    private final CalculationRequestRepository requests;
    private final CalculationResultRepository results;

    @EventListener
    @Transactional(timeoutString = "${app.calculation.transaction.write-timeout-seconds}")
    public void handle(@Valid CalculationRequested event) {
        log.debug("Handling calculation request for date {} with {} numbers", event.date(), event.numbers().size());

        var request = requests.save(new CalculationRequestRecord(null, event.date(), join(event.numbers())));
        var sum = event.numbers().stream().mapToLong(Integer::longValue).sum();
        var result = results.save(new CalculationResultRecord(null, request.id(), Long.toString(sum)));

        log.info("Stored calculation request id={} and result id={} (sum={})", request.id(), result.id(), result.sum());
        event.onCompleted().accept(new CalculationCompleted(result.id(), result.sum()));
    }

    @Transactional(readOnly = true, timeoutString = "${app.calculation.transaction.read-timeout-seconds}")
    public Optional<CalculationCompleted> find(@Positive long resultId) {
        log.debug("Reading calculation result {}", resultId);
        return results.findById(resultId).map(CalculationService::toCompleted);
    }

    private static CalculationCompleted toCompleted(CalculationResultRecord result) {
        return new CalculationCompleted(result.id(), result.sum());
    }

    private static String join(List<Integer> numbers) {
        return numbers.stream().map(String::valueOf).collect(joining(","));
    }
}
