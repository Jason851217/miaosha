package com.learningcenter.miaosha.dao;

import com.learningcenter.miaosha.dto.GoodsDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:28
 **/
public interface GoodsDao {
    @Select("select mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date,g.* from  miaosha_goods mg \n" +
            "left join goods g\n" +
            "on mg.goods_id=g.id")
    List<GoodsDto> listGoodsVo();

    @Select("select mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date,g.* from  miaosha_goods mg \n" +
            "left join goods g\n" +
            "on mg.goods_id=g.id\n" +
            "where g.id=#{0}")
    GoodsDto getGoodsById(long goodsId);
}
