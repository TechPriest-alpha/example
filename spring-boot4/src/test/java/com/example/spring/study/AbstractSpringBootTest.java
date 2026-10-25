package com.example.spring.study;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.spring.modulith.study.MyStudyApp;

/**
 * Base class for tests that need a fully started Spring context.
 * Single source of truth for the test profile name.
 */
@SpringBootTest(classes = MyStudyApp.class)
@ActiveProfiles(AbstractSpringBootTest.TEST_PROFILE)
public abstract class AbstractSpringBootTest {

    /** Profile that activates {@code src/test/resources/application-test.properties}. */
    public static final String TEST_PROFILE = "test";
}
