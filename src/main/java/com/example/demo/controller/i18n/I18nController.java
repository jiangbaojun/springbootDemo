package com.example.demo.controller.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/i18n")
public class I18nController {

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @Autowired
    @Qualifier("myMessageSource")
    private MessageSource myMessageSource;

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request){
        String username = messageSource.getMessage("username", null, LocaleContextHolder.getLocale());
        model.addAttribute("username", username);
        return "i18nIndex";
    }

    /**
     * http://localhost:88/i18n/test?lang=zh_tw
     */
    @RequestMapping("/test")
    @ResponseBody
    public String test1(Model model, HttpServletRequest request){
        String username = myMessageSource.getMessage("username", null, LocaleContextHolder.getLocale());
        //LocaleContextHolder.getLocale()获取当前的local，通过拦截LocaleChangeInterceptor，拦截lang参数，调用localeResolver.setLocale(...)设置
        String username1 = messageSource.getMessage("username", null, LocaleContextHolder.getLocale());
        return username +":"+ username1;
    }

}