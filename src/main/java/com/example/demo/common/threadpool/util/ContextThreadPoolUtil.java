package com.example.demo.common.threadpool.util;

import com.example.demo.common.threadpool.context.ContextThreadPoolExecutor;
import com.example.demo.util.SpringContextHolder;
import org.springframework.stereotype.Component;

/**
 * 线程池工具栏
 * @author jiangbaojun
 * @date 2023/4/10 17:13
 */
@Component
public class ContextThreadPoolUtil {

    /**
     * 获取线程池
     * @param name 线程池名称
     * @return com.example.demo.common.threadpool.context.ContextThreadPoolExecutor
     * @date 2023/4/10 17:13
     */
    public ContextThreadPoolExecutor getThreadPool(String name){
        return SpringContextHolder.getApplicationContext().getBean(name, ContextThreadPoolExecutor.class);
    }
}
