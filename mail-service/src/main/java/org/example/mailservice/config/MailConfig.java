package org.example.mailservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
@ConfigurationPropertiesScan
public class MailConfig {

    private String host;

    private String username;

    private String password;

    private int port;

    private String protocol;

    private String debug;

    private String url;

    private String email;

    private String subject;

    private String text;

    @Value("${mail.mail-protocol}")
    private String mailProtocol;

    @Value("${mail.mail-debug}")
    private String mailDebug;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty(mailProtocol, protocol);
        properties.setProperty(mailDebug, debug);
        return mailSender;
    }

}
