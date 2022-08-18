package com.example.demo.common.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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

	/**
     * 注册拦截器到容器中
     * 相当于是每次请求拦截参数，获取local信息，然后设置localeResolver.setLocale(...)
     * 实际中也可以通过head，用户登录信息、调用切换语言的接口，来设置localeResolver.setLocale(...)
     * 详见org.springframework.web.servlet.i18n.LocaleChangeInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor1 = registry.addInterceptor(localeChangeInterceptor());
        interceptor1.addPathPatterns("/**");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        //指定properties文件位置。查找classpath目录下的/properties/i18n目录，以test开头的properties文件（文件名采用test加上本地化的字符）
        messageSource.setBasename("classpath:/i18n/test");
        //可以设置多个目录
//        messageSource.setBasenames("classpath:/properties/i18n1", "classpath:/properties/i18n2");
//        messageSource.setCacheSeconds(10); //reload messages every 10 seconds,不建议生产设置
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
