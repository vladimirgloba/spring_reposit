package com.example.MyBookShopApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);
        mailProperties.put("mail.smtp.starttls.required", true);
        mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost("smtp.yandex.ru");
        mailSender.setPort(587);
        mailSender.setProtocol("smtp");
        mailSender.setUsername("vladimir-globa@yandex.ru");
        mailSender.setPassword("*************");
        return mailSender;
    }
}