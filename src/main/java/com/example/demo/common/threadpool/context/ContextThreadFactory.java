package com.example.demo.common.threadpool.context;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ContextThreadFactory implements ThreadFactory{

    private final AtomicInteger threadCount = new AtomicInteger();

    private ThreadGroup threadGroup;

    public ContextThreadFactory(ThreadGroup threadGroup) {
        this.threadGroup = threadGroup;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(threadGroup, runnable, nextThreadName());
        thread.setDaemon(false);
        return thread;
    }

    private String nextThreadName() {
        return threadGroup.getName() + "-" + this.threadCount.incrementAndGet();
    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }
}
