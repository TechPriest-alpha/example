package com.example.spring.modulith.study.data.repositories;

import com.example.spring.modulith.study.data.dto.CalculationRequestRecord;
import org.springframework.data.repository.CrudRepository;

public interface CalculationRequestRepository extends CrudRepository<CalculationRequestRecord, Long> {
}
