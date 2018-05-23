package com.learningcenter.miaosha.access;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 用于请求接口限流的注解
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {
	int seconds(); // 秒
	int maxCount(); // 最大访问次数
	boolean needLogin() default true; // 访问请求接口是否需要登录
}
