package com.example.spring.modulith.study.data.dto;

import org.springframework.data.annotation.Id;

public record CalculationResultRecord(@Id Long id, Long requestId, String sum) {

    public static CalculationResultRecord of(CalculationRequestRecord request, long sum) {
        return new CalculationResultRecord(null, request.id(), Long.toString(sum));
    }
}
