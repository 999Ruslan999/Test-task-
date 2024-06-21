package org.example.mailservice.handler;

import org.example.mailservice.data.event.EmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "registration-new-events-topic")
public class EmailEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(EmailEvent event){

        LOGGER.info("Получено сообщение: {}", event.getMail());

    }

}

