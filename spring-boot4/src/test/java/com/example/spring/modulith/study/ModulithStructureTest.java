package com.example.spring.modulith.study;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModulithStructureTest {

    @Test
    void verifiesModuleStructure() {
        var modules = ApplicationModules.of(MyStudyApp.class);
        modules.verify();

        assertEquals(4, modules.stream().count(), "expected api, calculation, data and configuration modules");
    }
}
