package es.urjc.code.monolito.service;

import es.urjc.code.monolito.ui.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogNotificationService implements NotificationService {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public void sendNotification(String message) {
        logger.info(message);
    }
}
