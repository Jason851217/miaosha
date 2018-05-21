package com.learningcenter.miaosha.controller;

import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.model.Goods;
import com.learningcenter.miaosha.model.MiaoShaOrder;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.model.OrderInfo;
import com.learningcenter.miaosha.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 15:13
 **/
@Controller
public class MiaoShaController {
    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoShaService miaoShaService;

    @RequestMapping("/do_miaosha")
    public String miaosha(Model model, MiaoShaUser user, Long goodsId) {
        if (user == null) {
            return "login";
        }

        //判断库存
        GoodsDto goods = goodsService.getGoodsById(goodsId);
        int stock = goods.getStock_count();
        if (stock <= 0) {
            model.addAttribute("errormsg", Result.CodeMsg.NO_STOCK);
            return "miaosha_failure";
        }
        //判断是否已经秒杀到了
        MiaoShaOrder miaoShaOrder = orderService.getMiaoShaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        //Java8 Optional的使用方式
        Optional<MiaoShaOrder> orderOptional = Optional.ofNullable(miaoShaOrder);
        if (orderOptional.isPresent()) {
            model.addAttribute("errormsg", Result.CodeMsg.MIAOSHA_REPEATE);
            return "miaosha_failure";
        }
        //1.减库存 2.下订单 3.写入秒杀订单
        OrderInfo orderInfo = miaoShaService.miaosha(user,goods); //为什么该接口要返回订单信息？因为我们希望秒杀成功之后，显示订单详细信息
        model.addAttribute("user", user);
        model.addAttribute("goods",goods);
        model.addAttribute("orderInfo",orderInfo);
       return "order_detail";
    }

}
