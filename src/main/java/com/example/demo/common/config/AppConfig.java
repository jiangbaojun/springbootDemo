package com.example.demo.common.config;

import com.example.demo.common.filter.MyFilter;
import com.example.demo.common.interceptor.MyInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 配置
 */
@Configuration
@EnableConfigurationProperties(Dept.class)
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        InterceptorRegistration interceptor1 = registry.addInterceptor(new MyInterceptor());
        interceptor1.addPathPatterns("/user/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态资源
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload1/**").addResourceLocations("classpath:/upload1/");
    }

    @Bean
    public FilterRegistrationBean myFiletr() {
        //过滤器
        FilterRegistrationBean filter1 = new FilterRegistrationBean(new MyFilter());
        filter1.addUrlPatterns("/user/*");
        return filter1;
    }
}
