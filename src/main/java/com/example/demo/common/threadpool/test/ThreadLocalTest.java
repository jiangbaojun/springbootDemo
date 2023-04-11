package com.example.demo.common.threadpool.test;

public class ThreadLocalTest<T> {

    public static ThreadLocal<UserExt> user = new ThreadLocal<>();
    public static ThreadLocal<UserExt> dataSourceKey = new ThreadLocal<>();

    private  final ThreadLocal<T> COMMON_FEIGN_PARAMETER_THREAD_LOCAL=new ThreadLocal<>();
    public  synchronized  void setParameter( T parameter)
    {
        COMMON_FEIGN_PARAMETER_THREAD_LOCAL.set(parameter);
    }
    public  T getParameter()
    {
        return COMMON_FEIGN_PARAMETER_THREAD_LOCAL.get();
    }
    public  void cleanParameter()
    {
        COMMON_FEIGN_PARAMETER_THREAD_LOCAL.remove();
    }
}
