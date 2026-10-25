package com.example.spring.modulith.study.calculation;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CalculationRequested(
    @NotNull LocalDate date,
    @NotEmpty List<@NotNull Integer> numbers,
    @NotNull Consumer<CalculationCompleted> onCompleted) {
}
