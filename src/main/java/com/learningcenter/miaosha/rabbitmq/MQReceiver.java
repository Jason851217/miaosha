package com.learningcenter.miaosha.rabbitmq;

import com.learningcenter.miaosha.configuration.RabbitMQConfiguration;
import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.dto.MiaoShaMessage;
import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.model.MiaoShaOrder;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.model.OrderInfo;
import com.learningcenter.miaosha.service.GoodsService;
import com.learningcenter.miaosha.service.MiaoShaService;
import com.learningcenter.miaosha.service.OrderService;
import com.learningcenter.miaosha.service.RedisService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoShaService miaoShaService;

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

    @RabbitListener(queues = "miaosha_queue")
    public void receiveMiaoSha(String msg){
        MiaoShaMessage miaoShaMessage =  RedisService.convertString2Bean(msg,MiaoShaMessage.class);
        Long goodsId = miaoShaMessage.getGoodsId();
        MiaoShaUser user = miaoShaMessage.getUser();
       //判断库存
        GoodsDto goods = goodsService.getGoodsById(goodsId);//10个商品，req1 req2
        int stock = goods.getStock_count();
        if(stock <= 0) {
            return ;
        }
        //判断是否已经秒杀到了
        MiaoShaOrder order = orderService.getMiaoShaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(order != null) {
            return ;
        }
        //减库存 下订单 写入秒杀订单
        miaoShaService.miaosha(user, goods);
    }
}
