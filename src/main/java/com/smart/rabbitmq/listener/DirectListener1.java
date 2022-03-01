package com.smart.rabbitmq.listener;

import com.smart.entity.po.Mail;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DirectListener1 {
    @RabbitListener(queues="directqueue1")
    public void displayMail(Mail mail) throws Exception {
        System.out.println("directqueue1队列监听器1号收到消息"+mail.toString());
    }

    /**
     * @RabbitHandler 只接收map数据
     * @param msg
     * @throws Exception
     */
    @RabbitListener(queues="directqueue1")
    @RabbitHandler
    public void displayMap(Map msg) throws  Exception{
        System.out.println("1.----directqueue1队列监听器1号收到消息  : " + msg.toString());
    }
}
