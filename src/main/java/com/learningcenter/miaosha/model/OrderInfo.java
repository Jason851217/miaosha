package com.learningcenter.miaosha.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 * 订单信息实体
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:20
 **/
public class OrderInfo implements Serializable {
    private long id;
    private long goods_id;
    private long user_id;
    private long delivery_addr_id;
    private String goods_name;
    private int goods_count;
    private double goods_price;
    private int order_channel;
    private int status;
    private Date create_date;
    private Date pay_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getDelivery_addr_id() {
        return delivery_addr_id;
    }

    public void setDelivery_addr_id(long delivery_addr_id) {
        this.delivery_addr_id = delivery_addr_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public int getOrder_channel() {
        return order_channel;
    }

    public void setOrder_channel(int order_channel) {
        this.order_channel = order_channel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getPay_date() {
        return pay_date;
    }

    public void setPay_date(Date pay_date) {
        this.pay_date = pay_date;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", goods_id=" + goods_id +
                ", user_id=" + user_id +
                ", delivery_addr_id=" + delivery_addr_id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_count=" + goods_count +
                ", goods_price=" + goods_price +
                ", order_channel=" + order_channel +
                ", status=" + status +
                ", create_date=" + create_date +
                ", pay_date=" + pay_date +
                '}';
    }
}
