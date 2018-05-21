package com.learningcenter.miaosha.service.impl;

import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.model.OrderInfo;
import com.learningcenter.miaosha.service.GoodsService;
import com.learningcenter.miaosha.service.MiaoShaService;
import com.learningcenter.miaosha.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 15:40
 **/
@Service
public class MiaoShaServiceImpl implements MiaoShaService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;


    @Override
    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, GoodsDto goods) {
        //1.减库存 2.下订单 3.写入秒杀订单
        goodsService.reduceStock(goods); // 减库存，利用数据库的乐观锁解决超卖问题，更新是行锁，不会出现两个线程同时更新1条记录的情况
        OrderInfo orderInfo = orderService.createOrder(user, goods); // 下订单  order_info miaosha_order  注意：为了防止一个用户发出对同一个商品的多个秒杀请求，我们利用数据中的唯一索引，这里，我们在miaosha_order中将userid和goodsid建一个唯一索引

        return orderInfo;
    }
}
