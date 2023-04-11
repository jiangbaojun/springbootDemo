package com.example.demo.common.async;

import com.example.demo.common.async.properties.AsyncProperties;
import com.example.demo.common.async.thread.MyThreadPoolTaskExecutor;
import com.example.demo.util.SpringContextHolder;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 覆盖默认Async线程池
 * @author jiangbaojun
 * @date 2023/4/4 16:00
 */
@EnableAsync
@Configuration
public class DefaultAsyncTaskExecutePool implements AsyncConfigurer {

    @Autowired
    private AsyncProperties asyncProperties;

    @Autowired
    private TaskExecutionProperties properties;

    public static final String DEFAULT_TASK_EXECUTOR = "default_task_executor";

    /**
     * 提供默认的ThreadPoolTaskExecutor
     * 由于只要定义了ThreadPoolTaskExecutor bean，就会使用。如果有多个，优先使用@Primary注解的，详见：org.springframework.aop.interceptor.AsyncExecutionAspectSupport#getDefaultExecutor(org.springframework.beans.factory.BeanFactory)
     * 如果@Primary注解了多个，无法确定默认，会创建一个SimpleAsyncTaskExecutor作为默认。详见：org.springframework.aop.interceptor.AsyncExecutionInterceptor#getDefaultExecutor(org.springframework.beans.factory.BeanFactory)
     * 如果使用了自定义ThreadPoolTaskExecutor，但是又不想影响默认的。此处需要定义默认的
     * @date 2023/4/6 20:09
     */
    @Primary
    @Bean
    @ConditionalOnProperty(name="com.mrk.async.auto-create-default", havingValue = "true", matchIfMissing = false)
    public ThreadPoolTaskExecutor applicationTaskExecutor(TaskExecutorBuilder builder) {
        return builder.build();
    }

    @Bean(DEFAULT_TASK_EXECUTOR)
    @ConditionalOnProperty(name="com.mrk.async.override-default-executor", havingValue = "true", matchIfMissing = false)
    public Executor defaultAsyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new MyThreadPoolTaskExecutor(properties, asyncProperties, DEFAULT_TASK_EXECUTOR);
        return executor;
    }

    /**
     * 只有@Async注解，不指定value值时会调用，用于覆盖默认的ThreadPoolTaskExecutor
     * @date 2023/4/6 20:11
     */
    @Override
    public Executor getAsyncExecutor() {
        if(asyncProperties.getOverrideDefaultExecutor()){
            return SpringContextHolder.getApplicationContext().getBean(DEFAULT_TASK_EXECUTOR, ThreadPoolTaskExecutor.class);
        }
        return null;
    }

     /**
     * 异步任务异常处理
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncUncaughtExceptionHandler();
    }
}