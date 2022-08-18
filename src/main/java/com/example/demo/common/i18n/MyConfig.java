package com.example.demo.common.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@Component("i18nConfig")
public class MyConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver(){
    	//添加自己重写的MyCookieLocaleResolver
        return new MyCookieLocaleResolver();
    }

	//配置拦截器获取URL中的key=“lang” （?lang=zh_CN）
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

	//注册拦截器到容器中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor1 = registry.addInterceptor(localeChangeInterceptor());
        interceptor1.addPathPatterns("/**");
    }
}
