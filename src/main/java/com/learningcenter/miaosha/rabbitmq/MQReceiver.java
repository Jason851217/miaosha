package com.learningcenter.miaosha.rabbitmq;

import com.learningcenter.miaosha.configuration.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 描述:
 * @RabbitListener注解如果加到类上，对该类中所有添加了@RabbitHandler注解的方法有效
 *
 * @RabbitListener注解如果加到方法上，对该方法有效
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-22 13:14
 **/
@Component
public class MQReceiver {

    @RabbitListener(queues = RabbitMQConfiguration.queue) // 监听哪个队列，然后从这个队列中取出消息消费
    public void receive(String msg){
        System.out.println(RabbitMQConfiguration.queue+" queue receive msg:"+msg);
    }

    @RabbitListener(queues = RabbitMQConfiguration.message)
    public void receiveTopic(String msg){
        System.out.println(RabbitMQConfiguration.message+" queue recieve msg:"+msg);
    }

    @RabbitListener(queues = RabbitMQConfiguration.messages)
    public void receiveTopics(String msg){
        System.out.println(RabbitMQConfiguration.messages+" queue recieve msg:"+msg);
    }

    @RabbitListener(queues = RabbitMQConfiguration.HEADERS_QUEUE)
    public void receiveHeader(byte[] msg){
        System.out.println(RabbitMQConfiguration.HEADERS_QUEUE+" queue recieve msg:"+new String(msg));
    }
}
