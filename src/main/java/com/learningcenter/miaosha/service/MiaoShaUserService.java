package com.learningcenter.miaosha.service;

import com.learningcenter.miaosha.dto.LoginDto;
import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.model.MiaoShaUser;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 22:31
 **/
public interface MiaoShaUserService {
    MiaoShaUser getUserById(long id);

    boolean login(LoginDto loginDto);
}
