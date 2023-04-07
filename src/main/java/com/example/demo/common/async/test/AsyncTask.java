package com.example.demo.common.async.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncTask {

    /**
     * myAsyncTaskPool 线程池的方法名，此处如果不写，会使用Spring默认的线程池
     */
    @Async("myAsyncTaskPool")
    public void customAsync(int i){
        try {
            ThreadLocalTest.local.set("custom:"+i);
            ThreadLocalTest.inheritLocal.set("custom:"+i);
            if(i==2){
                int a = 5/0;
            }
            log.info("custom：("+Thread.currentThread().getName()+")"+ i);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }finally {
//            ThreadLocalTest.local.remove();
//            ThreadLocalTest.inheritLocal.remove();
        }
    }

    @Async
    public void defaultAsync(int i){
        try {
            ThreadLocalTest.local.set("default:"+i);
            if(i==2){
                int a = 5/0;
            }
            log.info("override default：("+Thread.currentThread().getName()+")"+ i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ThreadLocalTest.local.remove();
        }
    }
}
