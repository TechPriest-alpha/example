package com.example.spring.modulith.study.calculation.dto;

import java.util.function.Consumer;

import com.example.spring.generated.study.model.CalculationRequest;
import com.example.spring.modulith.study.calculation.DomainEvent;
import jakarta.validation.constraints.NotNull;

public record CalculationRequested(
    @NotNull CalculationRequest request,
    @NotNull Consumer<CalculationCompleted> onCompleted) implements DomainEvent {

    public static CalculationRequested from(CalculationRequest request,
            Consumer<CalculationCompleted> onCompleted) {
        return new CalculationRequested(request, onCompleted);
    }

    public long total() {
        return request.getNumbers().stream().mapToLong(Integer::longValue).sum();
    }
}
