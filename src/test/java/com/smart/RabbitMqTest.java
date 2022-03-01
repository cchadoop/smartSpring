package com.smart;

import com.smart.entity.po.Mail;
import com.smart.service.Producer;
import com.smart.service.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class RabbitMqTest {

    @Autowired
    private Publisher publisher;

    @Autowired
    private Producer producer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void directExchangeTest() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息携带绑定键值：orange direct
        rabbitTemplate.convertAndSend("direct", "orange", map);
    }

    @Test
    public void directExchangeTest2() {
        Mail mail = new Mail("1", "china", 1.0);
        publisher.senddirectMail(mail, "orange");
    }

    @Test
    public void producerConsumerTest() {
        Mail mail = new Mail("1", "china", 1.0);
        producer.sendMail("myqueue",mail);
    }
}
