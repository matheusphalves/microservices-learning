package com.ms.user.producers;

import com.ms.user.dtos.MailRecordDTO;
import com.ms.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.ms.mail.name}")
    private String routingKey;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publishEmailMessage(UserModel userModel) {

        var emailRecordDTO = new MailRecordDTO(
                userModel.getUserId(),
                userModel.getMailAddress(),
                buildSubject(),
                this.buildTextMessage(userModel.getName())
        );

        rabbitTemplate.convertAndSend("", routingKey, emailRecordDTO);

    }

    private String buildTextMessage(String userName) {
        return String.format("Hi %s, this is an automatic mailAddress created for educational purposes. " +
                "This mailAddress has been sent because this mailAddress address has been associated with user in our application. " +
                "Please be careful using the internet, don't open suspect links or even download unknown files. " +
                "This message was possible to be sent due the use of the Spring Framework and the RabbitMQ built in a microservice architectural approach. " +
                "Best regards, Matheus", userName);
    }

    private String buildSubject(){
        return "No reply - the user has been created successfully!";
    }


}
