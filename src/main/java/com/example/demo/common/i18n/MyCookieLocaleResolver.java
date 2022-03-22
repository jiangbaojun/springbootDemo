package com.example.demo.common.i18n;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class MyCookieLocaleResolver extends CookieLocaleResolver {
    //重写构造方法,改变cookie信息
    public MyCookieLocaleResolver(){
        this.setCookieName("locale");
        //cookie有效期30天
        this.setCookieMaxAge(30*24*60*60);
        System.out.println("设置cookie参数成功！");
    }
}
