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
        goodsService.reduceStock(goods); // 减库存
        OrderInfo orderInfo = orderService.createOrder(user, goods); // 下订单  order_info miaosha_order

        return orderInfo;
    }
}
