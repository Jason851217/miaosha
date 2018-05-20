package com.learningcenter.miaosha.controller;

import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.service.MiaoShaUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 描述:
 * 登录控制器
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 21:42
 **/
@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }


    @RequestMapping("/do_login")
    @ResponseBody
    public Result login(LoginDto loginDto){
        String mobile = loginDto.getMobile();
        String pwd = loginDto.getPassword();
        log.info(loginDto.toString());
        if(StringUtils.isEmpty(pwd)){
            return Result.error(Result.CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            return Result.error(Result.CodeMsg.MOBILE_EMPTY);
        }
        Result.CodeMsg codeMsg =  miaoShaUserService.login(loginDto);
        if(codeMsg.getCode()!=0){
            return Result.error(codeMsg);
        }
        return Result.success(true);
    }

}
