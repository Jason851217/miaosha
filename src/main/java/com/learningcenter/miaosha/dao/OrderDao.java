package com.learningcenter.miaosha.dao;

import com.learningcenter.miaosha.model.MiaoShaOrder;
import com.learningcenter.miaosha.model.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:29
 **/
public interface OrderDao {
    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    MiaoShaOrder getMiaoShaOrderByUserIdAndGoodsId(@Param("userId")Long user_id, @Param("goodsId")Long goods_id);

    @Insert("insert into order_info(user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status,create_date,pay_date)\n" +
            "values(#{user_id},#{goods_id},#{delivery_addr_id},#{goods_name},#{goods_count},#{goods_price},#{order_channel},#{status},#{create_date},#{pay_date})")
    @SelectKey(keyProperty = "id",keyColumn = "id",resultType = Long.class,before = false,statement = "select last_insert_id()")
    Long addOrderInfo(OrderInfo orderInfo);

    @Insert("insert into miaosha_order(user_id,goods_id,order_id) values(#{user_id},#{goods_id},#{order_id})")
    int addMiaoShaOrder(MiaoShaOrder miaoShaOrder);
}
