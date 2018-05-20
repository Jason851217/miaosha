package com.learningcenter.miaosha.service.impl;

import com.learningcenter.miaosha.service.UserService;
import com.learningcenter.miaosha.dao.UserMapper;
import com.learningcenter.miaosha.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 13:49
 **/
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserById(int userId) {
        return userMapper.getUserById(userId);
    }
}
