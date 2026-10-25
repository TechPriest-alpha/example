package com.example.spring.modulith.study.data;

import org.springframework.data.annotation.Id;

public record CalculationResultRecord(@Id Long id, Long requestId, String sum) {
}
