package com.smart.service.impl;

import com.smart.entity.po.Mail;
import com.smart.service.Publisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherImpl implements Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishMail(Mail mail) {

    }

    @Override
    public void senddirectMail(Mail mail, String routingkey) {
        rabbitTemplate.convertAndSend("direct", routingkey, mail);
    }

    @Override
    public void sendtopicMail(Mail mail, String routingkey) {

    }
}
