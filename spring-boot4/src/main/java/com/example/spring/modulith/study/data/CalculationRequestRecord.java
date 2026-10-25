package com.example.spring.modulith.study.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public record CalculationRequestRecord(@Id Long id, LocalDate requestDate, String numbers) {
}
