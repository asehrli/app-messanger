package org.example.appsimplemassenger.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailSmsServiceImpl implements SendSmsService {
    private final JavaMailSender javaMailSender;

    @Async
    public void send(String to, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            mimeMessage.setFrom("asehrli@gmail.com");
            mimeMessage.setText(text);
            mimeMessage.setRecipients(Message.RecipientType.TO, to);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(mimeMessage);
    }
}
