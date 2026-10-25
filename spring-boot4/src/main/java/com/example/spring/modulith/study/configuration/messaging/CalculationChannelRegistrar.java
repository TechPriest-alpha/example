package com.example.spring.modulith.study.configuration.messaging;

import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

/**
 * Example of batch channel registration: one bean definition per channel name,
 * all of the same type, instead of a {@code @Bean} method per channel.
 */
@NullMarked
class CalculationChannelRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerChannels(registry, DirectChannel.class, CalculationChannels.REQUESTS, CalculationChannels.QUERIES);
    }

    private static void registerChannels(
        BeanDefinitionRegistry registry,
        Class<? extends MessageChannel> channelType,
        String... names
    ) {
        for (var name : names) {
            if (!registry.containsBeanDefinition(name)) {
                registry.registerBeanDefinition(
                    name,
                    BeanDefinitionBuilder.genericBeanDefinition(channelType).getBeanDefinition()
                );
            }
        }
    }
}
