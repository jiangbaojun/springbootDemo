package com.example.demo.common.param;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 用于判定是否需要处理该参数分解，返回 true 为需要，并会去调用下面的方法resolveArgument。
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(String.class)
                && methodParameter.hasParameterAnnotation(MyParam.class);
    }

    /**
     * 真正用于处理参数分解的方法，返回的 Object 就是 controller 方法上的形参对象。
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        try {
            HttpServletRequest request
                    = (HttpServletRequest) nativeWebRequest.getNativeRequest();
            String paramName = methodParameter.getParameter().getName();
            Map<String, String[]> parameterMap = request.getParameterMap();
            String[] paramValues = parameterMap.get(paramName);
            if(paramValues!=null){
                return "myParam("+paramValues[0]+")";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}