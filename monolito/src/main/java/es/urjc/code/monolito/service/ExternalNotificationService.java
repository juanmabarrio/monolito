package es.urjc.code.monolito.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ExternalNotificationService implements NotificationService {

    @Autowired
    Queue queue;

    RabbitTemplate rabbitTemplate = new RabbitTemplate();


    @Override
    public void sendNotification(String message) {
        rabbitTemplate.convertAndSend("myQueue", "Hello, world!");
    }
}