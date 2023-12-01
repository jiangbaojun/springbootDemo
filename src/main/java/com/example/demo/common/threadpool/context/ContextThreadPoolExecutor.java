package com.example.demo.common.threadpool.context;

import com.example.demo.common.threadpool.ThreadGroupManager;
import com.example.demo.common.threadpool.test.ThreadLocalTest;
import com.example.demo.common.threadpool.test.UserExt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 自定义线程池
 * @author jiangbaojun
 * @date 2023/4/10 16:10
 */
public class ContextThreadPoolExecutor extends ThreadPoolExecutor implements ThreadGroupManager {

    /**线程执行前是否清除threadLocal变量*/
    private boolean beforeClear;

    private ThreadGroup threadGroup;

    protected ContextThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ContextThreadFactory threadFactory, RejectedExecutionHandler handler, boolean beforeClear) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadGroup = threadFactory.getThreadGroup();
        this.beforeClear = beforeClear;
        this.allowCoreThreadTimeOut(true);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
    }

    /**
     * 异步执行任务。执行前自动设置，执行后自动清除：userExt、dataSourceKey
     * @param task 业务逻辑
     * @param user 用户信息
     * @date 2023/4/10 17:42
     */
    public void executeWith(Runnable task, UserExt user) {
        executeWith(task, user, true);
    }

    /**
     * 异步执行任务。执行前自动设置，执行后自动清除：userExt、dataSourceKey
     * @param task 业务逻辑
     * @param user 用户信息
     * @date 2023/4/10 17:42
     */
    public <T> Future<T> submitWith(Callable<T> task, UserExt user) {
        return submitWith(task, user, true);
    }

    /**
     * 异步执行任务。执行前自动设置，执行后自动清除：userExt、dataSourceKey
     * @param task 业务逻辑
     * @param user 用户信息
     * @param isDataSourceKey 是否自动设置数据源
     * @date 2023/4/10 17:42
     */
    public void executeWith(Runnable task, UserExt user, boolean isDataSourceKey) {
        executeWith(task, user, isDataSourceKey, null, null);
    }

    /**
     * 异步执行任务。执行前自动设置，执行后自动清除：userExt、dataSourceKey
     * @param task 业务逻辑
     * @param user 用户信息
     * @param isDataSourceKey 是否自动设置数据源
     * @date 2023/4/10 17:42
     */
    public <T> Future<T> submitWith(Callable<T> task, UserExt user, boolean isDataSourceKey) {
        return submitWith(task, user, isDataSourceKey, null, null);
    }

    /**
     * 异步执行任务。执行前自动设置，执行后自动清除：userExt、dataSourceKey、feignParam
     * @param task 业务逻辑
     * @param user 用户信息
     * @param threadLocalTest feign参数context
     * @param feignParam feign参数
     * @date 2023/4/10 17:42
     */
    public void executeWith(Runnable task, UserExt user, boolean isDataSourceKey, ThreadLocalTest threadLocalTest, Object feignParam) {
        Map<ThreadLocalTest, Object> feignParams = new HashMap<>();
        feignParams.put(threadLocalTest, feignParam);
        executeWith(task, user, isDataSourceKey, feignParams);
    }

    /**
     * 异步执行任务。执行前自动设置，执行后自动清除：userExt、dataSourceKey、feignParam
     * @param task 业务逻辑
     * @param user 用户信息
     * @param threadLocalTest feign参数context
     * @param feignParam feign参数
     * @date 2023/4/10 17:42
     */
    public <T> Future<T> submitWith(Callable<T> task, UserExt user, boolean isDataSourceKey, ThreadLocalTest threadLocalTest, Object feignParam) {
        Map<ThreadLocalTest, Object> feignParams = new HashMap<>();
        feignParams.put(threadLocalTest, feignParam);
        return submitWith(task, user, isDataSourceKey, feignParams);
    }

    /**
     * 异步执行任务。执行前自动设置，执行后自动清除：userExt、dataSourceKey、feignParam
     * @param task 业务逻辑
     * @param user 用户信息
     * @param feignParams feign参数
     * @date 2023/4/10 17:42
     */
    public void executeWith(Runnable task, UserExt user, boolean isDataSourceKey, Map<ThreadLocalTest, Object> feignParams) {
        if(beforeClear){
            clearThreadLocalContext(isDataSourceKey, feignParams);
        }
        super.execute(()->{
            try {
                setUserExt(user);
                if(isDataSourceKey){
                    setDataSourceKey(user);
                }
                setFeignParam(feignParams);
                task.run();
            }finally {
                clearThreadLocalContext(isDataSourceKey, feignParams);
            }
        });
    }

    /**
     * 异步执行任务。执行前自动设置，执行后自动清除：userExt、dataSourceKey、feignParam
     * @param task 业务逻辑
     * @param user 用户信息
     * @param feignParams feign参数
     * @return java.util.concurrent.Future<T>
     * @date 2023/4/11 11:32
     */
    public <T> Future<T> submitWith(Callable<T> task, UserExt user, boolean isDataSourceKey, Map<ThreadLocalTest, Object> feignParams) {
        if(beforeClear){
            clearThreadLocalContext(isDataSourceKey, feignParams);
        }
        return super.submit(()->{
            try {
                setUserExt(user);
                if(isDataSourceKey){
                    setDataSourceKey(user);
                }
                setFeignParam(feignParams);
                return task.call();
            }finally {
                clearThreadLocalContext(isDataSourceKey, feignParams);
            }
        });
    }

    @Override
    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }

    private void clearThreadLocalContext(boolean isDataSourceKey, Map<ThreadLocalTest, Object> feignParams) {
        clearUserExt();
        if(isDataSourceKey) {
            clearDataSourceKey();
        }
        clearFeignParam(feignParams);
    }

    private void setUserExt(UserExt user) {
        ThreadLocalTest.user.set(user);
    }

    private void setDataSourceKey(UserExt user) {
        ThreadLocalTest.dataSourceKey.set(user);
    }

    private void clearDataSourceKey() {
        ThreadLocalTest.dataSourceKey.remove();
    }

    private void clearUserExt() {
        ThreadLocalTest.user.remove();
    }

    private void setFeignParam(Map<ThreadLocalTest, Object> feignParams) {
       if(feignParams!=null && feignParams.size()>0){
           for (Map.Entry<ThreadLocalTest, Object> entry : feignParams.entrySet()) {
               ThreadLocalTest key = entry.getKey();
               Object value = entry.getValue();
                if(key!=null && value!=null){
                    key.setParameter(value);
                }

           }
       }
    }

    private void clearFeignParam(Map<ThreadLocalTest, Object> feignParams) {
        if(feignParams!=null && feignParams.size()>0){
            for (Map.Entry<ThreadLocalTest, Object> entry : feignParams.entrySet()) {
                ThreadLocalTest key = entry.getKey();
                if(key!=null){
                    key.cleanParameter();
                }

            }
        }
    }
}
