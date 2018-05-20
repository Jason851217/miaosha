package com.learningcenter.miaosha.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.learningcenter.miaosha.dto.LoginDto;
import com.learningcenter.miaosha.dao.MiaoShaUserDao;
import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.exception.GlobalException;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.service.MiaoShaUserService;
import com.learningcenter.miaosha.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 22:31
 **/
@Service
public class MiaoShaUserServiceImpl implements MiaoShaUserService {

    @Autowired
    private MiaoShaUserDao miaoShaUserDao;

    @Override
    public MiaoShaUser getUserById(long id) {
        return miaoShaUserDao.getUserById(id);
    }

    @Override
    public boolean login(LoginDto loginDto) {
        if(loginDto == null){
            throw new GlobalException(Result.CodeMsg.SERVER_ERROR);
        }
        String mobile = loginDto.getMobile();
        String pwd = loginDto.getPassword();
        MiaoShaUser user = getUserById(Long.valueOf(mobile));
        if(user == null){
            throw new GlobalException(Result.CodeMsg.USER_NOT_EXISTS);
        }
        //验证密码
        String dbPwd = user.getPwd();
        String salt = user.getSalt();
        String usermd5Pwd = MD5Util.formPwd2dbPwd(pwd,salt);
        if(!StringUtils.equals(usermd5Pwd,dbPwd)){
            throw new GlobalException(Result.CodeMsg.PASSWORD_ERROR);
        }
        return true;
    }
}
