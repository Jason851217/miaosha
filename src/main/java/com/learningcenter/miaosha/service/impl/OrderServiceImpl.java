package com.learningcenter.miaosha.service.impl;

import com.learningcenter.miaosha.dao.OrderInfoDao;
import com.learningcenter.miaosha.model.MiaoShaOrder;
import com.learningcenter.miaosha.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    OrderInfoDao orderInfoDao;

    @Override
    public MiaoShaOrder getMiaoShaOrderByUserIdAndGoodsId(Long userId, Long goodsId) {
        return orderInfoDao.getMiaoShaOrderByUserIdAndGoodsId(userId, goodsId);
    }
}
