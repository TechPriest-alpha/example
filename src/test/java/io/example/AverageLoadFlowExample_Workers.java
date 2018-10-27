package io.example;

import io.example.api.CommonApi;
import io.example.dto.AccessCheckRequest;
import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.dto.PublicDataRequest;
import io.example.test.ExampleBase;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Slf4j
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AverageLoadFlowExample_Workers extends ExampleBase {


    protected void runBasicWorkload(final AnnotationConfigApplicationContext ctx, final ExecutorService pool) throws InterruptedException {
        val api = ctx.getBean(CommonApi.class);
        val start = Instant.now();

        val tasks = IntStream.range(0, 20)
            .mapToObj(i -> (Runnable) () -> {
                api.checkPrivateAccess(new AccessCheckRequest());
                api.insertPrivateData(new PrivateData());
                api.readPublicData(new PublicDataRequest());
                api.readPrivateData(new PrivateDataRequest());
                delayAvg();
                log.debug("Task done {}", i);
            })
            .map(pool::submit)
            .collect(Collectors.toList());

        tasks.forEach(task -> {
            try {
                log.debug("Result: {}", task.get());
            } catch (final Exception ex) {
                log.error("Error while waiting", ex);
            }
        });
        log.info("Execution took: {}", Duration.between(start, Instant.now()));

    }
}
