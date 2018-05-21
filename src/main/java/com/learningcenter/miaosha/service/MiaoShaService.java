package com.learningcenter.miaosha.service;

import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.model.OrderInfo;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 15:40
 **/
public interface MiaoShaService {
    OrderInfo miaosha(MiaoShaUser user, GoodsDto goods);
}
