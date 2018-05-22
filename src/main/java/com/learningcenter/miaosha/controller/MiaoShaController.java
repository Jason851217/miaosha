package com.learningcenter.miaosha.controller;

import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.dto.MiaoShaMessage;
import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.model.MiaoShaOrder;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.rabbitmq.MQSender;
import com.learningcenter.miaosha.service.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 15:13
 **/
@Controller
@RequestMapping("/miaosha")
public class MiaoShaController implements InitializingBean { // 实现了InitializingBean接口，在MiaoshaController装配时初始化Bean过程中调用afterPropertiesSet

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoShaService miaoShaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender sender;
    // 商品是否秒杀完毕的内存标记，用来减轻秒杀接口一部分压力
    private ConcurrentHashMap<Long, Boolean> localOverMap =  new ConcurrentHashMap<Long, Boolean>();

    //系统初始化，把商品库存数量加载到redis
    public void afterPropertiesSet() throws Exception {
        List<GoodsDto> goodsList = goodsService.listGoodsVo();
        if(goodsList == null) {
            return;
        }
        for(GoodsDto goods : goodsList) {
            redisService.set("goodsStock:"+goods.getId(), goods.getStock_count());
            localOverMap.put(goods.getId(), false);
        }
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model,MiaoShaUser user,
                                      long goodsId) {
        if(user == null) {
            return Result.error(Result.CodeMsg.SERVER_ERROR);
        }
        model.addAttribute("user", user);
        long result  =miaoShaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }




    @RequestMapping(value="/do_miaosha", method=RequestMethod.POST)
    public Result miaosha(Model model, MiaoShaUser user, Long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(Result.CodeMsg.SERVER_ERROR);
        }
        //内存标记，减少redis访问。如果当前秒杀已经完毕，直接抛弃后续的秒杀请求，从而减轻压力
        boolean over = localOverMap.get(goodsId);
        if(over) { // 如果当前商品已经被秒杀完，则秒杀结束
            return Result.error(Result.CodeMsg.MIAOSHA_OVER);
        }
        //预减库存
        long stock = redisService.decr("goodsStock:"+goodsId);//stock是减了之后的值
        if(stock < 0) { //指定商品没有库存
            localOverMap.put(goodsId, true); // 指定某个商品已经秒杀完毕
            return Result.error(Result.CodeMsg.MIAOSHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoShaOrder order = orderService.getMiaoShaOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(Result.CodeMsg.MIAOSHA_REPEATE);
        }
        //发送消息到消息队列
        MiaoShaMessage mm = new MiaoShaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendMiaoShaMessage(mm);
        return Result.success(0);//排队中


    	/*
    	//没有引入消息队列之前

    	//判断库存
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);//10个商品，req1 req2
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		return Result.error(CodeMsg.MIAO_SHA_OVER);
    	}
    	//判断是否已经秒杀到了
    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		return Result.error(CodeMsg.REPEATE_MIAOSHA);
    	}
    	//减库存 下订单 写入秒杀订单
    	OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);

        */

    }


}
