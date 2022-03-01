package com.smart.controller;

import com.smart.entity.po.Mail;
import com.smart.entity.po.TopicMail;
import com.smart.service.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RabbitMQController {

    @Autowired
    Publisher publisher;

    @RequestMapping(value="/direct",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void direct(@ModelAttribute("mail") TopicMail mail){
        Mail m=new Mail(mail.getMailId(),mail.getCountry(),mail.getWeight());
        publisher.senddirectMail(m, mail.getRoutingkey());
    }
}
