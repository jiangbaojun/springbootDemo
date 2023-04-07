package com.example.demo.common.async.thread;

@Deprecated
public class MyRunnable implements Runnable{

    private Runnable delegatedRunnable;

    public MyRunnable(Runnable delegatedRunnable) {
        this.delegatedRunnable = delegatedRunnable;
    }

    /**
     * 仅仅是在线程创建，第一次开始运行的时候执行。当执行其他任务是不在执行该方法
     * @return void
     * @date 2023/4/4 18:35
     */
    @Override
    public void run() {
        System.out.println("start");
        delegatedRunnable.run();
    }
}
