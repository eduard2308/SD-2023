package com.lab4.demo.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("pasca.eduard@gmail.com");
        msg.setTo(to);
        msg.setSubject("Testing from Spring Boot");
        msg.setText(text);

        javaMailSender.send(msg);
        System.out.println("Email sent to " + to);
    }
}
