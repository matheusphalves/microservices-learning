package com.ms.mail.services;

import com.ms.mail.enums.MailStatus;
import com.ms.mail.models.MailModel;
import com.ms.mail.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MailService {

    private final MailRepository mailRepository;
    private final JavaMailSender emailSender;

    @Value(value="${spring.mail.username}")
    private String emailFrom;

    public MailService(MailRepository mailRepository, JavaMailSender emailSender) {
        this.mailRepository = mailRepository;
        this.emailSender = emailSender;
    }

    public MailModel sendEmail(MailModel mailModel) {

        try{

            mailModel.setSendDate(LocalDateTime.now());
            mailModel.setMailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailModel.getMailTo());
            message.setSubject(mailModel.getSubject());
            message.setText(mailModel.getText());

            emailSender.send(message);

            mailModel.setStatus(MailStatus.SENT);

        }catch (MailException mailException) {
            mailModel.setStatus(MailStatus.ERROR);
        }

        return mailRepository.save(mailModel);
    }
}
