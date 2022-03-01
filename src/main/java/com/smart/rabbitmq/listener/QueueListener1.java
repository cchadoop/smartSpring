package com.smart.rabbitmq.listener;


import com.smart.entity.po.Mail;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class QueueListener1 {
	
	@RabbitListener(queues = "myqueue")
	public void displayMail(Mail mail) throws Exception {
		System.out.println("队列监听器1号收到消息"+mail.toString());
	}
}
