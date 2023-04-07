package com.example.demo.common.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Slf4j
public class MyAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {


	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {

		log.error("===="+ex.getMessage()+"====", ex);
		log.error("exception method:"+method.getName());
	}

}