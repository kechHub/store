package com.kech.store.config;

import com.kech.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 处理器拦截器的注册
 */
@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 完成拦截器的注册
        // 创建自定义的拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();
        // 配置白名单：存在在一个list集合中
        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")// 表示要拦截的url是什么
                .excludePathPatterns(patterns);
    }
}
