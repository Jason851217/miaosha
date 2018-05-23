package com.learningcenter.miaosha.service.impl;

import com.learningcenter.miaosha.dao.OrderDao;
import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.model.MiaoShaOrder;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.model.OrderInfo;
import com.learningcenter.miaosha.service.OrderService;
import com.learningcenter.miaosha.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngineManager;
import java.util.Date;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 15:26
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
     RedisService redisService;

    @Override
    public MiaoShaOrder getMiaoShaOrderByUserIdAndGoodsId(Long userId, Long goodsId) {
        MiaoShaOrder miaoShaOrder = redisService.get("miaoshaorder:"+userId+":"+goodsId,MiaoShaOrder.class);
        if(miaoShaOrder!=null){
            return miaoShaOrder;
        }

        miaoShaOrder =  orderDao.getMiaoShaOrderByUserIdAndGoodsId(userId, goodsId);
        if(miaoShaOrder!=null){
            redisService.set("miaoshaorder:"+userId+":"+goodsId,miaoShaOrder);
        }
        return miaoShaOrder;

    }

    @Override
    public OrderInfo createOrder(MiaoShaUser user, GoodsDto goods) {
        //商品订单
        OrderInfo orderInfo  =new OrderInfo();
        orderInfo.setCreate_date(new Date());
        orderInfo.setUser_id(user.getId());
        orderInfo.setDelivery_addr_id(0L);
        orderInfo.setGoods_id(goods.getId());
        orderInfo.setGoods_name(goods.getGoods_name());
        orderInfo.setGoods_price(goods.getMiaosha_price());
        orderInfo.setGoods_count(1);//购买一个
        orderInfo.setOrder_channel(1);//pc
        orderInfo.setStatus(0);
        orderDao.addOrderInfo(orderInfo);
         //秒杀order
        MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
        miaoShaOrder.setOrder_id(orderInfo.getId()); // 注意这里：orderId必须通过orderInfo.getId()得到
        miaoShaOrder.setGoods_id(goods.getId());
        miaoShaOrder.setUser_id(user.getId());
        orderDao.addMiaoShaOrder(miaoShaOrder);
        //将秒杀订单添加到redis中
        redisService.set("miaoshaorder:"+user.getId()+":"+goods.getId(),miaoShaOrder);

        return orderInfo;
    }
}
