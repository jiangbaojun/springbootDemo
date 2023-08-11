package com.example.demo.common.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 * 使用@WebFilter注解的过滤器，按照类名的顺序生效。 AFilter比ZFilter优先级高
 * 优先级比FilterRegistrationBean要低
 * @Date 2018/12/20 15:56
 **/
@WebFilter(urlPatterns = "/*")
public class AFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("A过滤器-before");
        filterChain.doFilter(request, response);
        System.out.println("A过滤器-after");
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void destroy() {
    }
}
