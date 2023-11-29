package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDTO;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailRepository emailRepository;

    public EmailConsumer(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @RabbitListener(queues = "${broker.queue.ms.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDTO emailRecordDTO) {

        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailRecordDTO, emailModel);

        System.out.println(emailRecordDTO.emailTo());
    }

}
