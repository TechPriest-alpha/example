package com.example.spring.modulith.study.calculation.dto;

import com.example.spring.modulith.study.calculation.DomainEvent;
import com.example.spring.modulith.study.data.dto.CalculationResultRecord;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CalculationCompleted(@PositiveOrZero long id, @NotNull String sum) implements DomainEvent {

    public static CalculationCompleted from(CalculationResultRecord result) {
        return new CalculationCompleted(result.id(), result.sum());
    }
}
