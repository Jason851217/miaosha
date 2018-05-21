package com.learningcenter.miaosha.dao;

import com.learningcenter.miaosha.model.MiaoShaOrder;
import org.apache.ibatis.annotations.Select;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:29
 **/
public interface OrderInfoDao {
    @Select("select * from miaosha_order where user_id=#{0} and goods_id=#{1}")
    MiaoShaOrder getMiaoShaOrderByUserIdAndGoodsId(Long userId, Long goodsId);
}
