package com.learningcenter.miaosha.model;

import java.io.Serializable;

/**
 * 描述:
 * 秒杀订单实体
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:22
 **/
public class MiaoShaOrder implements Serializable {

    private Long id;
    private Long user_id;
    private Long goods_id;
    private Long order_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "MiaoShaOrder{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", goods_id=" + goods_id +
                ", order_id=" + order_id +
                '}';
    }
}
