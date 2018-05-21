package com.learningcenter.miaosha.model;

import java.io.Serializable;

/**
 * 描述:
 *商品实体
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:15
 **/
public class Goods implements Serializable {
    private Long id;
    private String goods_name;
    private String goods_title;
    private String goods_img;
    private String goods_detail;
    private Double goods_price;
    private Integer goods_stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(String goods_detail) {
        this.goods_detail = goods_detail;
    }

    public Double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }

    public Integer getGoods_stock() {
        return goods_stock;
    }

    public void setGoods_stock(Integer goods_stock) {
        this.goods_stock = goods_stock;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_title='" + goods_title + '\'' +
                ", goods_img='" + goods_img + '\'' +
                ", goods_detail='" + goods_detail + '\'' +
                ", goods_price=" + goods_price +
                ", goods_stock=" + goods_stock +
                '}';
    }
}
