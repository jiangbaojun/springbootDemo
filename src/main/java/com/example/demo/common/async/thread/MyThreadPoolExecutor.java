package com.example.demo.common.async.thread;

import com.example.demo.common.async.test.ThreadLocalTest;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 直接继承ThreadPoolExecutor
 * @author jiangbaojun
 * @date 2023/4/6 10:24
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 父级方法是protected，必须直接继承ThreadPoolExecutor才可以
     * @date 2023/4/6 10:23
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        ThreadLocalTest.common.set(""+new Random().nextInt(100));
        System.out.println("start common:"+ThreadLocalTest.common.get());

    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("after common:"+ThreadLocalTest.common.get());
        ThreadLocalTest.common.remove();
    }
}
