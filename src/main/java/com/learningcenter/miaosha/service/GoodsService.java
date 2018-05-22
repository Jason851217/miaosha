package com.learningcenter.miaosha.service;

import com.learningcenter.miaosha.dto.GoodsDto;

import java.util.List;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:28
 **/
public interface GoodsService {
    List<GoodsDto> listGoodsVo();

    GoodsDto getGoodsById(long goodsId);

    boolean reduceStock(GoodsDto goods);
}
