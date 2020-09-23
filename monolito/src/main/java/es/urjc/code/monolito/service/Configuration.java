package es.urjc.code.monolito.service;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    @ConditionalOnProperty(
            prefix = "notification",
            name = "service",
            havingValue ="log",
            matchIfMissing = true)
    public NotificationService defaultNotificationService() {
        return new LogNotificationService();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "notification",
            name = "service",
            havingValue ="external")
    public NotificationService externalNotificationService() {
        return new ExternalNotificationService();
    }

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue", false);
    }

}

