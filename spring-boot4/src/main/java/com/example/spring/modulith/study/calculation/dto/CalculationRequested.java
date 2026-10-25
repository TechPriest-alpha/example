package com.example.spring.modulith.study.calculation.dto;

import com.example.spring.generated.study.model.CalculationRequest;
import jakarta.validation.constraints.NotNull;

public record CalculationRequested(@NotNull CalculationRequest request) {

    public static CalculationRequested from(CalculationRequest request) {
        return new CalculationRequested(request);
    }

    public long total() {
        return request.getNumbers().stream().mapToLong(Integer::longValue).sum();
    }
}
