package com.wd.ASFlowerWeb.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wd.ASFlowerWeb.interceptor.AdminLoginInterceptor;
import com.wd.ASFlowerWeb.interceptor.HomeLoginInterceptor;

/**
 * @author 风微轻
 *
 * 2019年1月7日
 * 
 * @desc 网站配置文件，使用拦截器用到
 * 
 * @remark springboot 1.5 靠重写WebMvcConfigurerAdapter， 2.0后靠实现WebMvcConfigurer接口
 */
@Configuration
public class WebConfig implements  WebMvcConfigurer {

	
//	无需重载，重载后加载静态资源文件反而失效了
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		// TODO Auto-generated method stub
		//指定静态资源文件
//		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/**");
		//指定模板文件
//		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/**");
//		WebMvcConfigurer.super.addResourceHandlers(registry);
//	}
	

	/**
	 * @author 风微轻
	 * @desc 添加拦截器和拦截规则
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new HomeLoginInterceptor()).addPathPatterns("/home/user/**").excludePathPatterns(Arrays.asList("/static/**","/templates/**","/home/login"));
		registry.addInterceptor(new AdminLoginInterceptor()).addPathPatterns("/admin/**").excludePathPatterns(Arrays.asList("/static/**","/templates/**","/admin/login","/admin/toLogin"));
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
