package org.example.mailservice.handler;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "registration-new-events-topic", groupId = "mail-notification-created-events")
@RequiredArgsConstructor
public class DocumentsHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(String message) {

        String[] events = message.substring(1, message.length() - 1).split(", ");
        for (String event : events) {
            LOGGER.info("Получено сообщение: {}", event);
        }
    }

}

