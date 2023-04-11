package com.example.demo.common.async;

import com.example.demo.common.async.properties.AsyncProperties;
import com.example.demo.common.async.thread.MyThreadPoolTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 自定义@Async线程池
 * 已在CustomTaskExecutePoolAutoConfig中自动创建线程
 * @author jiangbaojun
 * @date 2023/4/4 15:48
 */
//@EnableAsync
//@Configuration
@Deprecated
public class CustomAsyncTaskExecutePool {

    @Autowired
    private AsyncProperties asyncProperties;

    //默认配置。后续优化，可以与默认的配置分开
    @Autowired
    private TaskExecutionProperties properties;

    /**
     * 使用 @Async("myAsyncTaskPool")
     * @return java.util.concurrent.Executor
     * @date 2023/4/4 15:48
     */
    @Bean("myAsyncTaskPool")
    @ConditionalOnProperty(name="com.mrk.async.enable-custom", havingValue = "true", matchIfMissing = false)
    public Executor myAsyncTaskPool() {
        TaskExecutionProperties.Pool pool = properties.getPool();


        /**1、使用自定义的线程池*/
//        return new MyThreadPoolExecutor(
//                pool.getCoreSize(), pool.getMaxSize(), pool.getKeepAlive().getSeconds(), TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(pool.getQueueCapacity()), new MyThreadFactory(this.threadPrefix), new ThreadPoolExecutor.AbortPolicy());


        /**2、使用自定义的ThreadPoolTaskExecutor*/
        ThreadPoolTaskExecutor executor = new MyThreadPoolTaskExecutor(properties, asyncProperties,"myAsyncTaskPool");
        return executor;


        /**3、使用默认的ThreadPoolTaskExecutor，参数可以自定义*/
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //核心线程池大小
//        executor.setCorePoolSize(pool.getCoreSize());
//        //最大线程数
//        executor.setMaxPoolSize(pool.getMaxSize());
//        //队列容量
//        executor.setQueueCapacity(pool.getQueueCapacity());
//        //活跃时间
//        executor.setKeepAliveSeconds((int) pool.getKeepAlive().getSeconds());
//        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        //线程名字前缀
//        //executor.setThreadNamePrefix(threadPrefix+"-");
//        executor.setThreadFactory(new MyThreadFactory(threadPrefix));
//        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
//        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
//        // AbortPolicy：拒绝，抛异常
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
//        executor.initialize();
//        return executor;
    }
}