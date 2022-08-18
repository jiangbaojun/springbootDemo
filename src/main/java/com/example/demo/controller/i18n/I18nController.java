package com.example.demo.controller.i18n;

import org.springframework.beans.factory.annotation.Autowired;
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
    private MessageSource messageSource;
	
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
        //LocaleContextHolder.getLocale()获取当前的local，本例MyCookieLocaleResolver中获取
        return messageSource.getMessage("username", null, LocaleContextHolder.getLocale());
    }

}