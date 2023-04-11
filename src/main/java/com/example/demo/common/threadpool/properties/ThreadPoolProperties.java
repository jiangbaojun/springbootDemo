package com.example.demo.common.threadpool.properties;

import com.example.demo.common.threadpool.ThreadPoolConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程池配置
 * @date 2023/4/6 16:43
 */
@ConfigurationProperties(prefix = ThreadPoolConstant.CONFIG_PREFIX)
public class ThreadPoolProperties {
    /**是否启用,默认true*/
    private Boolean enable = true;
    /**线程执行前是否清除threadLocal变量*/
    private Boolean beforeClear;
    /**线程池*/
    private Map<String, Pool> pools = new HashMap<>();

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getBeforeClear() {
        return beforeClear;
    }

    public void setBeforeClear(Boolean beforeClear) {
        this.beforeClear = beforeClear;
    }

    public Map<String, Pool> getPools() {
        return pools;
    }

    public void setPools(Map<String, Pool> pools) {
        this.pools = pools;
    }
}
