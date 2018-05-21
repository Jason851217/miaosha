package com.learningcenter.miaosha.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.learningcenter.miaosha.dto.LoginDto;
import com.learningcenter.miaosha.dao.MiaoShaUserDao;
import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.exception.GlobalException;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.service.MiaoShaUserService;
import com.learningcenter.miaosha.service.RedisService;
import com.learningcenter.miaosha.utils.MD5Util;
import com.learningcenter.miaosha.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 22:31
 **/
@Service
public class MiaoShaUserServiceImpl implements MiaoShaUserService {

    public static final String TOKEN_PREFIX="token";
    public static final int TOKEN_EXPIRESECONDS=3000;
    public static final String COOKIE_TOKEN="token";

    @Autowired
    private MiaoShaUserDao miaoShaUserDao;
    @Autowired
    private RedisService redisService;

    @Override
    public MiaoShaUser getUserById(long id) {
        //取缓存
        MiaoShaUser user = redisService.get("miaoshauser:getById:"+id, MiaoShaUser.class);
        if(user != null) {
            return user;
        }
        //取数据库
        user = miaoShaUserDao.getUserById(id);
        if(user != null) {
            //加入到缓存
            redisService.set("miaoshauser:getById:"+id, user);
        }
        return user;
    }

    // http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        MiaoShaUser user = getUserById(id);
        if(user == null) {
            throw new GlobalException(Result.CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库（这里为什么要new一个对象而不是直接使用user对象，是因为更新的字段越多影响性能，所以修改哪些字段就更新哪些字段）
        MiaoShaUser toBeUpdate = new MiaoShaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPwd(MD5Util.formPwd2dbPwd(formPass, user.getSalt()));
        //先把数据存到数据库中，成功后，再让缓存失效
        miaoShaUserDao.update(toBeUpdate);
        //处理缓存(让和当前修改记录相关的所有缓存都要更新)
        redisService.delete("miaoshauser:getById:"+id);
        user.setPwd(toBeUpdate.getPwd());
        //更新token缓存
        redisService.set(TOKEN_PREFIX+token, user);
        return true;
    }

    @Override
    public boolean login(HttpServletResponse response , LoginDto loginDto) {
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
        String token = UUIDUtil.uuid();
        //管理用户信息
        addUserInfo(response, user,token);
        return true;
    }

    private void addUserInfo(HttpServletResponse response, MiaoShaUser user,String token) {
        //token没有必要每次都生成，只有当登录时才生成
        //解决分布式session：将用户信息写入redis
        redisService.set(TOKEN_PREFIX+token,user);
        //生成一个cookie,并设置有效期和path
        Cookie tokenCookie = new Cookie(COOKIE_TOKEN,token);
        tokenCookie.setMaxAge(TOKEN_EXPIRESECONDS);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
    }

    @Override
    public MiaoShaUser getByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoShaUser user = redisService.get(TOKEN_PREFIX+token,MiaoShaUser.class);
        if(user != null){
            //延长有效期
            addUserInfo(response, user,token);
        }

        return user;
    }
}
