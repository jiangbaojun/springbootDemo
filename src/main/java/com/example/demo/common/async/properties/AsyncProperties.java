package com.example.demo.common.async.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置属性
 * @date 2023/4/6 16:43
 */
@ConfigurationProperties("com.mrk.async")
public class AsyncProperties {
    /**是否覆盖默认的ThreadPoolTaskExecutor。true，覆盖@Async注解默认的线程池*/
    private Boolean overrideDefaultExecutor;
    /**是否已主动声明默认的ThreadPoolTaskExecutor（声明时可以使用@Primary注解）。false，会自动创建一个默认的*/
    private Boolean autoCreateDefault;
    /**是否自动创建config map配置的ThreadPoolTaskExecutor*/
    private Boolean autoCreateCustomer = false;
    private Map<String, AsyncPool> config = new HashMap<>();

    public Boolean getOverrideDefaultExecutor() {
        return overrideDefaultExecutor;
    }

    public void setOverrideDefaultExecutor(Boolean overrideDefaultExecutor) {
        this.overrideDefaultExecutor = overrideDefaultExecutor;
    }

    public Boolean getAutoCreateCustomer() {
        return autoCreateCustomer;
    }

    public void setAutoCreateCustomer(Boolean autoCreateCustomer) {
        this.autoCreateCustomer = autoCreateCustomer;
    }

    public Boolean getAutoCreateDefault() {
        return autoCreateDefault;
    }

    public void setAutoCreateDefault(Boolean autoCreateDefault) {
        this.autoCreateDefault = autoCreateDefault;
    }

    public Map<String, AsyncPool> getConfig() {
        return config;
    }

    public void setConfig(Map<String, AsyncPool> config) {
        this.config = config;
    }
}
