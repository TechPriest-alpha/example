package io.example.client.config;

import io.example.client.core.DataHandlerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ServiceLoader;

@Configuration
public class ChatClientFactoryProvider {
    private final ServiceLoader<DataHandlerFactory> dataHandlerProvider;

    public ChatClientFactoryProvider() {
        this.dataHandlerProvider = ServiceLoader.load(DataHandlerFactory.class);
    }

    @Bean
    public DataHandlerFactory dataHandler() {
        return dataHandlerProvider.findFirst().orElseThrow();
    }
}
