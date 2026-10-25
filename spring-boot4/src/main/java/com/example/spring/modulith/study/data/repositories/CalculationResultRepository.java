package com.example.spring.modulith.study.data.repositories;

import com.example.spring.modulith.study.data.dto.CalculationResultRecord;
import org.springframework.data.repository.CrudRepository;

public interface CalculationResultRepository extends CrudRepository<CalculationResultRecord, Long> {
}
