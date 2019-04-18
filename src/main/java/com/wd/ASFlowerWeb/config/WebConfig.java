package com.wd.ASFlowerWeb.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wd.ASFlowerWeb.interceptor.AdminLoginInterceptor;
import com.wd.ASFlowerWeb.interceptor.HomeLoginInterceptor;

/**
 * @author 韦丹
 *
 * 2019年1月7日
 * 
 * @desc 项目自定义配置文件，使用拦截器用到
 * 
 * @remark springboot 1.5 靠重写WebMvcConfigurerAdapter， 2.0后靠实现WebMvcConfigurer接口
 */
@Configuration
public class WebConfig implements  WebMvcConfigurer {

	

	/**
	 * @author 韦丹
	 * @desc 添加拦截器和拦截规则
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//用户拦截规则 --前台用户信息相关，未登录拦截
		registry.addInterceptor(new HomeLoginInterceptor()).addPathPatterns("/home/user/**").excludePathPatterns(Arrays.asList("/static/**","/templates/**","/home/login"));
		//订单拦截规则 --前台用户订单相关，未登录拦截
		registry.addInterceptor(new HomeLoginInterceptor()).addPathPatterns("/home/order/**").excludePathPatterns(Arrays.asList("/static/**","/templates/**","/home/login"));
		//后台拦截规则 --后台，除登录外，用户未登录拦截
		registry.addInterceptor(new AdminLoginInterceptor()).addPathPatterns("/admin/**").excludePathPatterns(Arrays.asList("/static/**","/templates/**","/admin/login","/admin/toLogin"));
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
