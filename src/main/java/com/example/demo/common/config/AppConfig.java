package com.example.demo.common.config;

import com.example.demo.common.DynamicDateSerialize;
import com.example.demo.common.async.properties.AsyncProperties;
import com.example.demo.common.filter.MyFilter;
import com.example.demo.common.interceptor.MyInterceptor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

/**
 * 配置
 */
@Configuration
@EnableConfigurationProperties({Dept.class, AsyncProperties.class})
public class AppConfig implements WebMvcConfigurer {

    private String DATE_FORMAT = "yyyy-MM-dd";
    private String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


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

    /**
     * 配置jackson
     * 此处配置是全局的。
     * 如果只需要部分接口或实体生效，不要在此处配置。在实体类属性上通过 @JsonSerialize(using=DynamicDateSerialize.class)注解实现
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new DynamicDateSerialize());
//            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
//            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }
}