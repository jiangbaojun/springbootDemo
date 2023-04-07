package com.example.demo.common.async.properties;

import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;

/**
 * 自定义aSync线程池配置
 * @author jiangbaojun
 * @date 2023/4/6 20:42
 */
public class AsyncPool {
    TaskExecutionProperties.Pool pool;
    private String threadGroupName;

    public TaskExecutionProperties.Pool getPool() {
        return pool;
    }

    public void setPool(TaskExecutionProperties.Pool pool) {
        this.pool = pool;
    }

    public String getThreadGroupName() {
        return threadGroupName;
    }

    public void setThreadGroupName(String threadGroupName) {
        this.threadGroupName = threadGroupName;
    }
}
