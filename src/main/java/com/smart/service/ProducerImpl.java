package com.smart.service;

import com.smart.entity.po.Mail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerImpl implements Producer{
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Override
    public void sendMail(String queue, Mail mail) {
        rabbitTemplate.setDefaultReceiveQueue(queue);
        rabbitTemplate.convertAndSend(mail);
    }
}
