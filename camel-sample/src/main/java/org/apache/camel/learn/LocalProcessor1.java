package org.apache.camel.learn;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalProcessor1 implements Processor {
    private static final Logger log = LoggerFactory.getLogger(LocalProcessor1.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Local processor handles {}", exchange.getMessage().getBody(String.class));
    }
}
