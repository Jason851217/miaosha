package com.learningcenter.miaosha.dto;


import com.learningcenter.miaosha.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 描述:
 * 使用了JSR303参数校验
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 21:55
 **/

public class LoginDto implements Serializable {
    @NotNull(message = "手机号不能为空")
//  @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\\\d{8}$",message = "手机号不合法")
    @IsMobile
    private String mobile;
    @NotNull(message = "密码不能为空")
    @Length(min = 6,max = 16,message = "密码长度介于6到16之间")
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
