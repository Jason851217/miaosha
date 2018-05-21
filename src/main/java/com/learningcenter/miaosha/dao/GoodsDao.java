package com.learningcenter.miaosha.dao;

import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.model.Goods;
import com.learningcenter.miaosha.model.MiaoShaGoods;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    //数据库的乐观锁解决超卖问题，更新是行锁，不会出现两个线程同时更新1条记录的情况
    @Update("update miaosha_goods set stock_count = stock_count -1 where goods_id=#{goods_id} and stock_count>0")
    int reduceStock(MiaoShaGoods miaoShaGoods);
}
