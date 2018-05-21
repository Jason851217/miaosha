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
    private Long id;
    private String nickname;
    private String pwd;
    private String salt;
    private String head;
    private Date register_date;
    private Date last_login_date;
    private Long login_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getLogin_count() {
        return login_count;
    }

    public void setLogin_count(Long login_count) {
        this.login_count = login_count;
    }

    @Override
    public String toString() {
        return "MiaoShaUser{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", salt='" + salt + '\'' +
                ", head='" + head + '\'' +
                ", register_date=" + register_date +
                ", last_login_date=" + last_login_date +
                ", login_count=" + login_count +
                '}';
    }
}
