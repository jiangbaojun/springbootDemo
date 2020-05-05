package com.example.demo.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 测试自动配置属性,配置勒种添加EnableConfigurationProperties注解支持
 * @Author: jiangbaojun
 * @Date: 2020/5/2 18:10
 */
@ConfigurationProperties("dept")
public class Dept {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    private String id;
    private String name;
}
