package es.urjc.code.monolito.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

        @Bean
        @ConditionalOnProperty(
                name = "notification.service",
                matchIfMissing = true)
        public NotificationService defaultNotificationService() {
            return new LogNotificationService();
        }
}

