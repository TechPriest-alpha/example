package io.example.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"io.example"})
@PropertySource("classpath:/properties/application.properties")
public class ChatServerConfig {
}
