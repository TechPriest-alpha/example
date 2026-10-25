package com.example.spring.modulith.study.calculation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CalculationCompleted(@PositiveOrZero long id, @NotNull String sum) {
}
