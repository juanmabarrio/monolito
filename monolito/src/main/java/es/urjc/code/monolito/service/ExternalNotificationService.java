package es.urjc.code.monolito.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


public class ExternalNotificationService implements NotificationService {

    @Autowired
    private final RabbitTemplate rabbitTemplate;


    public ExternalNotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendNotification(String message) {
        rabbitTemplate.convertAndSend("myQueue", message);


    }
}