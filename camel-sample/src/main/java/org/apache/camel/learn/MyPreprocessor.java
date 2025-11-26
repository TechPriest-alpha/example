package org.apache.camel.learn;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPreprocessor implements Processor {
    private static final Logger log = LoggerFactory.getLogger(MyPreprocessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        var data = exchange.getMessage().getBody(String.class);
        log.info("Wow! {}", data);
        var message = new DefaultMessage(exchange);
        message.setBody("In processed");
        exchange.setMessage(message);
    }
}
