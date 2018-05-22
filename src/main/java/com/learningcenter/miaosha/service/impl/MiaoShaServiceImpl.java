package com.learningcenter.miaosha.service.impl;

import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.model.MiaoShaOrder;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.model.OrderInfo;
import com.learningcenter.miaosha.service.GoodsService;
import com.learningcenter.miaosha.service.MiaoShaService;
import com.learningcenter.miaosha.service.OrderService;
import com.learningcenter.miaosha.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, GoodsDto goods) {
        //1.减库存 2.下订单 3.写入秒杀订单
        boolean result = goodsService.reduceStock(goods); // 减库存，利用数据库的乐观锁解决超卖问题，更新是行锁，不会出现两个线程同时更新1条记录的情况
        if(result){  // 减库存成功才下订单
            OrderInfo orderInfo = orderService.createOrder(user, goods); // 下订单  order_info miaosha_order  注意：为了防止一个用户发出对同一个商品的多个秒杀请求，我们利用数据中的唯一索引，这里，我们在miaosha_order中将userid和goodsid建一个唯一索引
            return orderInfo;
        }else{
            setGoodsOver(goods.getId()); // 减库存没有成功表示该商品已经被秒杀完
            return null;
        }

    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoShaOrder order = orderService.getMiaoShaOrderByUserIdAndGoodsId(userId, goodsId);
        if(order != null) {//生成订单表示秒杀成功
            return order.getOrder_id();
        }else { // 没有生成订单
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) { // 没有生成订单并且该商品已经被秒杀完，那么秒杀失败
                return -1;
            }else { //  没有生成订单并且该商品已经没有被秒杀完则还在排队处理，需等待
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set( "goodsover："+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists("goodsover："+goodsId);
    }

}
