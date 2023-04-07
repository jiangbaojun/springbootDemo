package com.example.demo.common.async.test;

public class ThreadLocalTest {

    public static ThreadLocal<String> local = new ThreadLocal<>();
    public static InheritableThreadLocal<String> inheritLocal = new InheritableThreadLocal<>();
}
