package com.example.demo.common.threadpool.properties;

/**
 * 线程池配置
 */
public class Pool {
    /**线程池配置*/
    private Config config;
    /**线程池内的线程所属线程组名称*/
    private String threadGroupName;
    /**线程执行前是否清楚threadLocal变量*/
    private Boolean beforeClear;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getThreadGroupName() {
        return threadGroupName;
    }

    public void setThreadGroupName(String threadGroupName) {
        this.threadGroupName = threadGroupName;
    }

    public Boolean getBeforeClear() {
        return beforeClear;
    }

    public void setBeforeClear(Boolean beforeClear) {
        this.beforeClear = beforeClear;
    }
}
