package org.example.mailservice.handler;

import lombok.RequiredArgsConstructor;
import org.example.mailservice.config.MailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "registration-new-events-topic", groupId = "mail-notification-created-events")
@RequiredArgsConstructor
public class EmailEventHandler {

    private final MailConfig mailConfig;

    private final JavaMailSender mailSender;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(String event) {

        LOGGER.info("Получено сообщение: {}", event);

        String mail = event;
        notificationRegistration(mail);


    }

    public void notificationRegistration(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailConfig.getUsername());
        message.setTo(email);
        message.setSubject(mailConfig.getSubject());
        message.setText(mailConfig.getText() + " Вы успешно зарегестрировались!");
        mailSender.send(message);
    }

}