package com.example.demo.common.i18n;

import com.example.demo.common.i18n.message.MyMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
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
//        return new MyCookieLocaleResolver();
        return new MyLocaleResolver();
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
    public MessageSource myMessageSource() {
        //可以多个共存，使用时分别注入
        return new MyMessageSource();
    }

    /**
     * 有自动配置，详见：org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration
     */
    @Bean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        //指定properties文件位置,使用自定义路径，需要file:开头
        messageSource.setBasenames("file:d:/data/i18n/test", "file:d:/data/i18n_common/test");
        //指定properties文件位置。查找classpath目录下的/properties/i18n目录，以test开头的properties文件（文件名采用test加上本地化的字符）
//        messageSource.setBasename("classpath:/i18n/test");
        //可以设置多个目录
//        messageSource.setBasenames("classpath:/properties/i18n1", "classpath:/properties/i18n2");
        messageSource.setCacheSeconds(8);
        messageSource.setDefaultEncoding("UTF-8");
        //当无法匹配，使用本地local
        messageSource.setFallbackToSystemLocale(false);
        //无法匹配，使用key作为message
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean("bundleMessageSource")
    public MessageSource bundleMessageSource(){
        ResourceBundleMessageSource resourceBundleMessageSource =new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setBasenames("i18n/test");
        return resourceBundleMessageSource;
    }

}
