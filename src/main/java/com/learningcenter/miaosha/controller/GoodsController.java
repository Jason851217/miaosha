package com.learningcenter.miaosha.controller;

import com.learningcenter.miaosha.dto.GoodsDto;
import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.exception.GlobalException;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.service.GoodsService;
import com.learningcenter.miaosha.service.MiaoShaUserService;
import com.learningcenter.miaosha.service.RedisService;
import com.learningcenter.miaosha.service.impl.MiaoShaUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 00:02
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static final Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;
//
//    @ModelAttribute   //1.@ModelAttribute注释void返回值的方法
//    public void populateModel(Model model) {
//        model.addAttribute("attributeName", "jason");
//    }


//    @ModelAttribute("user")  // 2.@ModelAttribute注释返回具体类的方法 注意：如果没有指定name或者value，那么model名称为方法返回类型的首字母小写，并且名称和返回值绑定，比如这里："user"=user
//    public MiaoShaUser user(HttpServletResponse response, @CookieValue(value = MiaoShaUserServiceImpl.COOKIE_TOKEN, required = false) String cookieToken,
//                       @RequestParam(value = MiaoShaUserServiceImpl.COOKIE_TOKEN, required = false) String paramToken){
//        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
//            //当cookie中没有token并且请求参数中没有token，则返回到登录页面
//            throw new GlobalException(Result.CodeMsg.SERVER_ERROR);
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken; //优先去哪个当中的token
//        MiaoShaUser user = miaoShaUserService.getByToken(response,token);
//        return user;
//    }
//
//    @RequestMapping("/to_list")//3.@ModelAttribute注释一个方法的参数  @ModelAttribute("user") MiaoShaUser user注释方法参数，参数user的值来源于user()方法中的model属性。此时如果方法体没有标注@SessionAttributes("user")，那么scope为request，如果标注了，那么scope为session
//    public String toGoodsList(Model model, @ModelAttribute("user") MiaoShaUser user) {
//        model.addAttribute("user",user);
//        return "good_list";
//    }

//    @RequestMapping("/to_list_default")
//    public String toGoodsList(Model model, HttpServletResponse response, @CookieValue(value = MiaoShaUserServiceImpl.COOKIE_TOKEN, required = false) String cookieToken,
//                              @RequestParam(value = MiaoShaUserServiceImpl.COOKIE_TOKEN, required = false) String paramToken) {
//        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
//            //当cookie中没有token并且请求参数中没有token，则返回到登录页面
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken; //优先去哪个当中的token
//        MiaoShaUser user = miaoShaUserService.getByToken(response, token);
//        model.addAttribute("user", user);
//        return "good_list";
//    }


    @RequestMapping("/to_list")
    public String toGoodsList(Model model,MiaoShaUser user) {
        model.addAttribute("user", user);
       List<GoodsDto> goodsList = goodsService.listGoodsVo();
       model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }
}
