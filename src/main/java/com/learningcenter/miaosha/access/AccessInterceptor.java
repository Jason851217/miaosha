package com.learningcenter.miaosha.access;

import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learningcenter.miaosha.dto.Result;
import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.service.MiaoShaUserService;
import com.learningcenter.miaosha.service.RedisService;
import com.learningcenter.miaosha.service.impl.MiaoShaUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

/**
 * 用于限流的拦截器
 */
@Service
public class AccessInterceptor  extends HandlerInterceptorAdapter{
	
	@Autowired
	MiaoShaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			MiaoShaUser user = getUser(request, response);
			UserContext.setUser(user); // ThreadLocal 线程安全，每个线程隔离
			HandlerMethod hm = (HandlerMethod)handler;
			AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class); // 获取方法上的AccessLimit注解
			if(accessLimit == null) { // 没有AccessLimit注解的方法不拦截
				return true;
			}
			int seconds = accessLimit.seconds();//获取注解属性
			int maxCount = accessLimit.maxCount();
			boolean needLogin = accessLimit.needLogin();
			String key = request.getRequestURI();//使用请求路径
			if(needLogin) { //如果需要登录
				if(user == null) { // 注解了需要登录，但是没有user，则拦截掉
					render(response, Result.CodeMsg.SERVER_ERROR);
					return false;
				}
				key += "_" + user.getId();
			}

			Integer count = redisService.get("access"+key, Integer.class); // redis获取对应接口访问的次数
	    	if(count  == null) { // 第一次访问，或者过了有效期
	    		 redisService.setEx("access:"+key, 1,seconds);
	    	}else if(count < maxCount) {
	    		 redisService.incr("access:"+key);
	    	}else { //count大于最大访问次数则拦截掉
	    		render(response, Result.CodeMsg.ACCESS_LIMIT_REACHED);
	    		return false;
	    	}
		}
		return true;
	}
	
	private void render(HttpServletResponse response, Result.CodeMsg cm)throws Exception {
		response.setContentType("application/json;charset=UTF-8");//设置相应内容类型
		OutputStream out = response.getOutputStream();
		String str  = JSON.toJSONString(Result.error(cm));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

	private MiaoShaUser getUser(HttpServletRequest request, HttpServletResponse response) {
		String paramToken = request.getParameter(MiaoShaUserServiceImpl.COOKIE_TOKEN);
		String cookieToken = getCookieValue(request, MiaoShaUserServiceImpl.COOKIE_TOKEN);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return userService.getByToken(response, token);
	}
	
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if(cookies == null || cookies.length <= 0){
			return null;
		}
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookiName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
