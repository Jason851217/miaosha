package com.learningcenter.miaosha.rabbitmq;

import com.learningcenter.miaosha.configuration.RabbitMQConfiguration;
import com.learningcenter.miaosha.service.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-22 13:14
 **/
@Component
public class MQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * Direct模式：消息直接发送到队列中
     * @param message
     */
    public void send(Object message){
        String msg = RedisService.convertBean2String(message);
        System.out.println("send msg:"+msg);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.queue,msg);
    }

    public void sendTopic(Object message){
        String msg = RedisService.convertBean2String(message);
        System.out.println("send msg:"+msg);
        rabbitTemplate.convertAndSend("exchange","topic.message",msg); // msg会被发送到exchange，然后通过路由key topic.message去路由到对应的队列，这里会匹配到两个队列：topic.message和topic.messages，最后消息会被转发到这两个队列中
    }

    public void sendFanoutExchange(Object message){
        String msg = RedisService.convertBean2String(message);
        System.out.println("send msg:"+msg);
        rabbitTemplate.convertAndSend("fanoutExchange","",msg); // msg会被发送到fanoutExchange绑定的所有队列，这里会将消息转发到my_queue和topic.message这两个队列中
    }




}
