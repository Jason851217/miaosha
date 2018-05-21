package com.learningcenter.miaosha.service.impl;

import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.model.OrderInfo;
import com.learningcenter.miaosha.service.MiaoShaService;
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

    @Override
    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, GoodsDto goods) {
     return null;
    }
}
