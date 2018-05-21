package com.learningcenter.miaosha.service;

import com.learningcenter.miaosha.model.MiaoShaOrder;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 15:24
 **/
public interface OrderService {

    MiaoShaOrder getMiaoShaOrderByUserIdAndGoodsId(Long userId,Long goodsId );
}
