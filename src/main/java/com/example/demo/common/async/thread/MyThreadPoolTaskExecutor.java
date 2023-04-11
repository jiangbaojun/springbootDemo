package com.example.demo.common.async.thread;

import com.example.demo.common.ThreadGroupManager;
import com.example.demo.common.async.DefaultAsyncTaskExecutePool;
import com.example.demo.common.async.properties.AsyncPool;
import com.example.demo.common.async.properties.AsyncProperties;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Field;
import java.util.concurrent.*;

/**
 * 自定义ThreadPoolTaskExecutor，
 * ThreadPoolTaskExecutor包含线程池属性，通过反射处理为自定义线程池
 * @author jiangbaojun
 * @date 2023/4/6 11:30
 */
public class MyThreadPoolTaskExecutor extends ThreadPoolTaskExecutor implements ThreadGroupManager {

    private int queueCapacity;
    private int coreSize;
    private int maxSize;
    private boolean allowCoreThreadTimeout;
    private int keepAlive;
    private String threadGroupName;

    private ThreadGroup threadGroup;

    private String defaultThreadGroupName = "override-async";

    public MyThreadPoolTaskExecutor(TaskExecutionProperties properties, AsyncProperties asyncProperties, String taskExecutorName) {
        if(taskExecutorName==null){
            throw new RuntimeException("task executor名称不可以为空");
        }
        TaskExecutionProperties.Pool pool;
        if(DefaultAsyncTaskExecutePool.DEFAULT_TASK_EXECUTOR.equals(taskExecutorName)){
            pool = properties.getPool();
            this.threadGroupName = defaultThreadGroupName;
        }else{
            AsyncPool asyncPool = asyncProperties.getConfig().get(taskExecutorName);
            if(asyncPool==null || asyncPool.getThreadGroupName()==null){
                throw new RuntimeException("线程组名称不可以为空");
            }
            pool = asyncPool.getPool();
            if(pool==null){
                //如果没有配置，使用默认的
                pool = properties.getPool();
            }
            this.threadGroupName = asyncPool.getThreadGroupName();
        }
        this.coreSize = pool.getCoreSize();
        this.maxSize = pool.getMaxSize();
        this.keepAlive = (int) pool.getKeepAlive().getSeconds();
        this.queueCapacity = pool.getQueueCapacity();
        this.allowCoreThreadTimeout = pool.isAllowCoreThreadTimeout();
    }

    @Override
    protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super.initializeExecutor(threadFactory, rejectedExecutionHandler);
        try {
            threadGroup = new ThreadGroup(threadGroupName);
            MyThreadPoolExecutor myThreadPoolExecutor = new MyThreadPoolExecutor(
                    coreSize, maxSize, keepAlive, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(queueCapacity), new MyThreadFactory(this.threadGroup), new ThreadPoolExecutor.AbortPolicy());
            myThreadPoolExecutor.allowCoreThreadTimeOut(allowCoreThreadTimeout);
            Class aClass = ThreadPoolTaskExecutor.class;
            Field field = aClass.getDeclaredField("threadPoolExecutor");
            field.setAccessible(true);
            field.set(this, myThreadPoolExecutor);
            return myThreadPoolExecutor;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得线程组
     */
    @Override
    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }
}
