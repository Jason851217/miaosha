package com.learningcenter.miaosha.dto;

import com.learningcenter.miaosha.model.Goods;

import java.util.Date;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 11:31
 **/
public class GoodsDto extends Goods {
    private Double miaosha_price;
    private Integer stock_count;
    private Date start_date;
    private Date end_date;

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
        return "GoodsDto{" +
                "miaosha_price=" + miaosha_price +
                ", stock_count=" + stock_count +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
