package com.ms.mail.consumers;

import com.ms.mail.dtos.MailRecordDTO;
import com.ms.mail.models.MailModel;
import com.ms.mail.services.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MailConsumer {

    private final MailService mailService;

    public MailConsumer(
            MailService mailService) {
        this.mailService = mailService;
    }

    @RabbitListener(queues = "${broker.queue.ms.mail.name}")
    public void listenEmailQueue(@Payload MailRecordDTO mailRecordDTO) {

        MailModel mailModel = new MailModel();
        BeanUtils.copyProperties(mailRecordDTO, mailModel);

        mailService.sendEmail(mailModel);
    }

}
