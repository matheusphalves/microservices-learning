package com.ms.mail.services;

import com.ms.mail.dtos.MailSubscriptionRecordDTO;
import com.ms.mail.enums.MailStatus;
import com.ms.mail.models.MailModel;
import com.ms.mail.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ms.mail.constants.SubscriptionConstants.EMAIL_SUBJECT;

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

    public MailModel sendUserAccountConfirmation(MailModel mailModel) {

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

    public MailModel sendUserSubscriptionConfirmation(MailSubscriptionRecordDTO mailSubscriptionRecordDTO){

        MailModel mailModel = new MailModel();
        try{

            mailModel.setSendDate(LocalDateTime.now());
            mailModel.setMailFrom(emailFrom);
            mailModel.setMailTo(mailSubscriptionRecordDTO.userEmailAddress());
            mailModel.setText(mailSubscriptionRecordDTO.emailContent());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailModel.getMailTo());
            message.setSubject(mailModel.getSubject());
            message.setText(mailModel.getText());
            message.setSubject(EMAIL_SUBJECT);

            emailSender.send(message);
            mailModel.setStatus(MailStatus.SENT);

        }catch (MailException mailException) {
            mailModel.setStatus(MailStatus.ERROR);
        }

        return mailRepository.save(mailModel);

    }
}
