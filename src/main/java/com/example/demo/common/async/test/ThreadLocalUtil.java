package com.example.demo.common.async.test;

import com.example.demo.common.threadpool.ThreadGroupManager;
import com.example.demo.util.SpringContextHolder;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * thread工具
 * @author jiangbaojun
 * @date 2023/4/6 15:55
 */
public class ThreadLocalUtil {

    /**
     * 获得指定线程组下的线程
     * @param poolBeanName ThreadGroupManager bean名称
     * @return java.lang.Thread[]
     * @date 2023/4/6 18:13
     */
    public static Thread[] getAllThreads(String poolBeanName){
        Thread[] threads = new Thread[0];
        try{
            ThreadGroupManager bean = SpringContextHolder.getBean(poolBeanName);
            ThreadGroup threadGroup = bean.getThreadGroup();
            int count = threadGroup.activeCount();
            threads = new Thread[count];
            threadGroup.enumerate(threads, true);
            for (Thread thread : threads) {
                System.out.println(thread.getName());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return threads;
    }

    /**
     * 获得线程下所有threadLocal变量
     * @param thread 线程
     * @return java.util.Map<java.lang.ref.WeakReference<java.lang.ThreadLocal>,com.example.demo.common.async.test.ThreadLocalUtil.ThreadLocalValue>
     * @date 2023/4/6 15:56
     */
    public static Map<WeakReference<ThreadLocal>, ThreadLocalValue> getThreadLocalMap(Thread thread){
        Map<WeakReference<ThreadLocal>, ThreadLocalValue> threadLocals = new HashMap<>();

        try{
            Object[] currentTable = getLocalTableArr(thread, "threadLocals");
            Object[] inheritTable = getLocalTableArr(thread, "inheritableThreadLocals");
            Object[] table = ArrayUtils.addAll(currentTable, inheritTable);
            for(int i=0;i<table.length;i++){
                Object entry = table[i];
                if(entry != null){

                    WeakReference<ThreadLocal> threadLocalRef = (WeakReference<ThreadLocal>)entry;
                    Field valueFiled = threadLocalRef.getClass().getDeclaredField("value");
                    valueFiled.setAccessible(true);
                    Object value = (Object) valueFiled.get(threadLocalRef);
                    if(threadLocalRef != null){
                        ThreadLocalValue threadLocalValue=new ThreadLocalValue();
                        threadLocalValue.setThread(thread);
                        threadLocalValue.setValue(value);
                        threadLocals.put(threadLocalRef, threadLocalValue);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return threadLocals;
    }

    private static Object[] getLocalTableArr(Thread thread, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field threadLocalsField = Thread.class.getDeclaredField(fieldName);
        threadLocalsField.setAccessible(true);
        Object threadLocalMap = threadLocalsField.get(thread);
        if(threadLocalMap==null){
            return new Object[]{};
        }
        Field tableField = threadLocalMap.getClass().getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[])tableField.get(threadLocalMap);
        return table;
    }

    public static void resetThreadLocals(Map<WeakReference<ThreadLocal>, ThreadLocalValue> threadLocals) throws NoSuchFieldException, IllegalAccessException {
        if(threadLocals == null){
            return;
        }
        for(Map.Entry<WeakReference<ThreadLocal>, ThreadLocalValue> entry : threadLocals.entrySet()){
            Thread thread= entry.getValue().getThread();
            Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            threadLocalsField.set(thread,null);
        }
    }

    @Data
    public static class ThreadLocalValue{
        private Thread thread;
        private Object value;
    }
}
