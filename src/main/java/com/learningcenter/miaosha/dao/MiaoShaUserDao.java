package com.learningcenter.miaosha.dao;

import com.learningcenter.miaosha.model.MiaoShaUser;
import org.apache.ibatis.annotations.Select;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 22:28
 **/
public interface MiaoShaUserDao {
    @Select("select * from miaosha_user where id=#{0}")
    MiaoShaUser getUserById(long id);
}
