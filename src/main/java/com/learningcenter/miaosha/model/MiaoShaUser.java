package com.learningcenter.miaosha.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 * 用户实体
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 21:40
 **/
public class MiaoShaUser implements Serializable {
    private long id;
    private String nickname;
    private String pwd;
    private String salt;
    private String head;
    private Date register_date;
    private Date last_login_date;
    private int login_count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public Date getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(Date last_login_date) {
        this.last_login_date = last_login_date;
    }

    public int getLogin_count() {
        return login_count;
    }

    public void setLogin_count(int login_count) {
        this.login_count = login_count;
    }
}
