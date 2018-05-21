package com.learningcenter.miaosha.configuration;

import com.learningcenter.miaosha.model.MiaoShaUser;
import com.learningcenter.miaosha.service.MiaoShaUserService;
import com.learningcenter.miaosha.service.RedisService;
import com.learningcenter.miaosha.service.impl.MiaoShaUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 描述:
 * 添加一个MiaoShaUser类的参数解析器，实现HandlerMethodArgumentResolver即可，HandlerMethodArgumentResolver有很多子类实现，比如PageableHandlerMethodArgumentResolver用来处理Pageable参数解析
 *
 *
 * 在springBoot 2 中需要注意，因为使用的是spring5了，原先的方法的是 继承 webMvcConfigurerAdapter抽象类，现在是直接扩展webMvcConfigurer这个接口。原先的方式还能生效，不过已经被5弃用了（@deprecated）
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-21 01:10
 **/
@Configuration
//
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    private MiaoShaUserService miaoShaUserService;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter methodParameter) { // 支持哪些方法参数
                Class<?> clazz = methodParameter.getParameterType();
                return clazz == MiaoShaUser.class;
            }

            @Override
            public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
                HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
                HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
                String paramToken = request.getParameter(MiaoShaUserServiceImpl.COOKIE_TOKEN);
                String cookieToken = getCookieValue(request, MiaoShaUserServiceImpl.COOKIE_TOKEN);
                if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
                    return null;
                }
                String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
                MiaoShaUser user = miaoShaUserService.getByToken(response, token);
                return user;
            }

            private String getCookieValue(HttpServletRequest request, String cookieToken) {
                Cookie[] cookies = request.getCookies();
                //Java8 中的Stream
                Optional<Cookie> cookie = Stream.of(cookies).filter((cookieItem) -> {
                            return StringUtils.equals(cookieItem.getName(), cookieToken);
                        }
                ).limit(1).findFirst();
                if (cookie.isPresent()) {
                    return cookie.get().getValue();
                }
                return null;
            }
        });
    }
}
