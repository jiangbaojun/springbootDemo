package com.example.demo.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 全局异常
 * 通常处理的是ajax,当然也可以是页面
 *
 * 异常页面，找固定的页面，如/error、 /error/404
 */
@ControllerAdvice
public class MyExceptionHandler {

//    @ExceptionHandler(value =Exception.class)
    @ExceptionHandler
	@ResponseBody
	public String exceptionHandler1(HttpServletRequest request, Exception e) throws IOException {
		System.out.println("未知异常:"+e);
		request.setAttribute("errMsg", "异常信息");
       	return "发生异常:"+e.getMessage();
    }

//    @ExceptionHandler(value =Throwable.class)
//	public ModelAndView exceptionHandler2(Throwable ex) throws IOException {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("error/error");
//		modelAndView.addObject("errMsg", ex.getMessage());
//		return modelAndView;
//    }
}