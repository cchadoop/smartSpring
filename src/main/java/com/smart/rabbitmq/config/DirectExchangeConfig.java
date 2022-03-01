package com.smart.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//direct直连模式的交换机配置,包括一个direct交换机，两个队列，三根网线binding

//队列 起名：TestDirectQueue
// durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
// exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
// autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
@Configuration
public class DirectExchangeConfig {


    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange("direct");
        return directExchange;
    }

    @Bean
    public Queue directQueue1() {
        Queue queue=new Queue("directqueue1");
        return queue;
    }

    @Bean
    public Queue directQueue2() {
        Queue queue=new Queue("directqueue2");
        return queue;
    }

    //3个binding将交换机和相应队列连起来
    @Bean
    public Binding bindingorange(){
        Binding binding= BindingBuilder.bind(directQueue1()).to(directExchange()).with("orange");
        return binding;
    }

    @Bean
    public Binding bindingblack(){
        Binding binding=BindingBuilder.bind(directQueue2()).to(directExchange()).with("black");
        return binding;
    }

    @Bean
    public Binding bindinggreen(){
        Binding binding=BindingBuilder.bind(directQueue2()).to(directExchange()).with("green");
        return binding;
    }



}
