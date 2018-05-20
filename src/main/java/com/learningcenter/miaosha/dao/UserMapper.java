package com.learningcenter.miaosha.dao;

import com.learningcenter.miaosha.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 13:41
 **/
public interface UserMapper {
    @Select("select id,name from user where id=#{0}")
    User getUserById(int id);
}
