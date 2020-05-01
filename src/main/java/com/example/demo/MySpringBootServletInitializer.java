package com.example.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 使用war部署项目，使用的是servlet3.0的特性，作为入口
 * 步骤
 * 1、修改pom.xml文件
 *  修改打包方式<packaging>war</packaging>
 *  修改tomcat包的引入范围
 *         <dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-starter-tomcat</artifactId>
 *             <scope>provided</scope>
 *         </dependency>
 * 2、添加该入口启动类
 */
public class MySpringBootServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HelloWorldApplication.class);
    }
}