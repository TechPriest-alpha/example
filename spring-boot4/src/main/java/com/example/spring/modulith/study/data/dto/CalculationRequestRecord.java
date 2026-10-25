package com.example.spring.modulith.study.data.dto;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;

import com.example.spring.generated.study.model.CalculationRequest;
import org.springframework.data.annotation.Id;

public record CalculationRequestRecord(@Id Long id, LocalDate requestDate, String numbers) {

    public static CalculationRequestRecord of(CalculationRequest request) {
        return new CalculationRequestRecord(
            null,
            request.getDate(),
            request.getNumbers().stream().map(String::valueOf).collect(joining(","))
        );
    }
}
