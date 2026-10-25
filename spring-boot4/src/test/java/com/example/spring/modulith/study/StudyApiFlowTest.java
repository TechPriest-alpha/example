package com.example.spring.modulith.study;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Comparator;
import java.util.stream.StreamSupport;

import com.example.spring.modulith.study.data.dto.CalculationResultRecord;
import com.example.spring.modulith.study.data.repositories.CalculationResultRepository;
import com.example.spring.study.AbstractSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class StudyApiFlowTest extends AbstractSpringBootTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CalculationResultRepository results;

    @Test
    void calculateStoresRequestAndResult() throws Exception {
        mockMvc.perform(post("/api/study/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2026-10-25\",\"numbers\":[1,2,3]}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].sum").value("6"));

        var resultId = latestResultId();

        mockMvc.perform(get("/api/study/{id}", resultId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.number").value(resultId))
            .andExpect(jsonPath("$.text").value("6"));
    }

    @Test
    void unknownResultReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/study/{id}", 999_999L))
            .andExpect(status().isNotFound());
    }

    private long latestResultId() {
        return StreamSupport.stream(results.findAll().spliterator(), false)
            .max(Comparator.comparing(CalculationResultRecord::id))
            .orElseThrow()
            .id();
    }
}
