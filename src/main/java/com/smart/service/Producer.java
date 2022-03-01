package com.smart.service;

import com.smart.entity.po.Mail;

public interface Producer {
     void sendMail(String queue, Mail mail);//向队列queue发送消息
}
