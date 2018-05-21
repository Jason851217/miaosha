package com.learningcenter.miaosha.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 * 秒杀商品实体
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:17
 **/
public class MiaoShaGoods implements Serializable {
    private Long id;
    private Long goods_id;
    private Double miaosha_price;
    private Integer stock_count;
    private Date start_date;
    private Date end_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Double getMiaosha_price() {
        return miaosha_price;
    }

    public void setMiaosha_price(Double miaosha_price) {
        this.miaosha_price = miaosha_price;
    }

    public Integer getStock_count() {
        return stock_count;
    }

    public void setStock_count(Integer stock_count) {
        this.stock_count = stock_count;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "MiaoShaGoods{" +
                "id=" + id +
                ", goods_id=" + goods_id +
                ", miaosha_price=" + miaosha_price +
                ", stock_count=" + stock_count +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
