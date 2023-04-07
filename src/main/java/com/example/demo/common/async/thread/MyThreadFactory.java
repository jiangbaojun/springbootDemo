package com.example.demo.common.async.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory implements ThreadFactory{

    private final AtomicInteger threadCount = new AtomicInteger();

    private ThreadGroup threadGroup;

    public MyThreadFactory(ThreadGroup threadGroup) {
        this.threadGroup = threadGroup;
    }

    @Override
    public Thread newThread(Runnable runnable) {
//        Thread thread = new Thread(new ThreadGroup(threadGroupName), new MyRunnable(runnable), nextThreadName());
        Thread thread = new Thread(threadGroup, runnable, nextThreadName());
        thread.setDaemon(false);
        return thread;
    }

    private String nextThreadName() {
        return threadGroup.getName() + "-" + this.threadCount.incrementAndGet();
    }
}
