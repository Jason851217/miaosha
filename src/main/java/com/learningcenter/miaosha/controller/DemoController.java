package com.learningcenter.miaosha.controller;

import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.rabbitmq.MQSender;
import com.learningcenter.miaosha.service.RedisService;
import com.learningcenter.miaosha.service.UserService;
import com.learningcenter.miaosha.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 11:26
 **/
@Controller
public class DemoController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;


    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","Jason");
        return "hello";
    }

    @RequestMapping("/user/{id}")
    @ResponseBody
    public User user(@PathVariable(name = "id") int userId){
        User user = userService.getUserById(userId);
        return user;
    }

    @RequestMapping("/jedis/get")
    @ResponseBody
    public Object jedis(){
        User value = redisService.get("User:getById:"+1,User.class);
        return Result.success(value);
    }

    @RequestMapping("/jedis/set")
    @ResponseBody
    public Object jedisSet(){
        User user = userService.getUserById(1);
        boolean value = redisService.set("User:getById:"+1,user);
        return Result.success(value);
    }


    @RequestMapping("/mq")
    @ResponseBody
    public Object mq(){
        User user = userService.getUserById(1);
        mqSender.send(user);
        return Result.success(user);
    }

    @RequestMapping("/mq_topic")
    @ResponseBody
    public Object topic(){
        User user = userService.getUserById(1);
        mqSender.sendTopic(user);
        return Result.success(user);
    }

    @RequestMapping("/mq_fanout")
    @ResponseBody
    public Object fanout(){
        User user = userService.getUserById(1);
        mqSender.sendFanoutExchange(user);
        return Result.success(user);
    }
}
