package com.learningcenter.miaosha.dto;

import com.learningcenter.miaosha.model.MiaoShaUser;

import java.io.Serializable;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-22 15:48
 **/
public class MiaoShaMessage implements Serializable {
    private MiaoShaUser user;
    private Long goodsId;

    public MiaoShaUser getUser() {
        return user;
    }

    public void setUser(MiaoShaUser user) {
        this.user = user;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

}
