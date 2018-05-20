package com.learningcenter.miaosha.controller;

import com.learningcenter.miaosha.dto.LoginDto;
import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.service.MiaoShaUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    /**
     * JSR303参数校验
     * @param loginDto
     * @param loginDtoValidResult
     * @return
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result login(@Valid LoginDto loginDto, BindingResult loginDtoValidResult){
//        if(loginDtoValidResult.hasErrors()){
//            for (ObjectError error : loginDtoValidResult.getAllErrors()) {
//                log.info(error.getDefaultMessage());
//            }
//        }
        //@Valid LoginDto loginDto 验证异常都将被全局异常处理起处理
        log.info(loginDto.toString());
        //login操作抛出的异常都将被全局异常处理起处理
        miaoShaUserService.login(loginDto);
        return Result.success(true);
    }

}
