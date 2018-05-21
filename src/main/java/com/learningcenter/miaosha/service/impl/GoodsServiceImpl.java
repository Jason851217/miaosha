package com.learningcenter.miaosha.service.impl;

import com.learningcenter.miaosha.dao.GoodsDao;
import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:29
 **/
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<GoodsDto> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }
}
