package com.example.demo.common.threadpool.test;

import com.example.demo.common.threadpool.context.ContextThreadPoolExecutor;
import com.example.demo.common.threadpool.util.ContextThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class ThreadTestService {

    @Autowired
    @Qualifier("myPool2")
    private ContextThreadPoolExecutor myPool2;

    @Autowired
    private ContextThreadPoolUtil contextThreadPoolUtil;

    ThreadLocalTest<String> t1 = new ThreadLocalTest<>();
    ThreadLocalTest<Integer> t2 = new ThreadLocalTest<>();

    public void test(){
        ContextThreadPoolExecutor threadPool = contextThreadPoolUtil.getThreadPool("myPool1");
        UserExt userExt = new UserExt();
        userExt.setUserId(999);
        Map<ThreadLocalTest, Object> map = new HashMap<>();
        map.put(t1, "abc");
        map.put(t2, 123);

//        for (int i = 0; i < 1000; i++) {
//            printContext("before");
//            threadPool.executeWith(()->{
//                System.out.println("task execution");
//                printContext("executing");
//            },userExt, true, map);
//        }

        for (int i = 0; i < 100; i++) {
            printContext("before");
            myPool2.executeWith(()->{
                System.out.println("task execution");
                printContext("executing");
            },userExt);
        }
    }

    public void test2() throws ExecutionException, InterruptedException {
        ContextThreadPoolExecutor threadPool = contextThreadPoolUtil.getThreadPool("myPool1");
        UserExt userExt = new UserExt();
        userExt.setUserId(666);
        Map<ThreadLocalTest, Object> map = new HashMap<>();
        map.put(t1, "def");
        map.put(t2, 456);

        for (int i = 0; i < 1000; i++) {
            printContext("before");
            Future<String> future = threadPool.submitWith(() -> {
                System.out.println("task submit");
                printContext("executing");
                return "ok";
            }, userExt, true, map);
            String result = future.get();
            System.out.println(result);
            printContext("after");
        }
    }

    private void printContext(String phase) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("----------"+phase+"------------");
        stringBuffer.append("\r\n");
        stringBuffer.append(ThreadLocalTest.user.get());
        stringBuffer.append("\r\n");
        stringBuffer.append(ThreadLocalTest.dataSourceKey.get());
        stringBuffer.append("\r\n");
        stringBuffer.append(t1.getParameter());
        stringBuffer.append("\r\n");
        stringBuffer.append(t2.getParameter());
        stringBuffer.append("\r\n");
        System.out.println(stringBuffer.toString());
    }
}