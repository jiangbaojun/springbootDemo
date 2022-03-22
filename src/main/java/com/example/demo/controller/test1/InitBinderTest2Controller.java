package com.example.demo.controller.test1;

import com.example.demo.model.user.Student;
import com.example.demo.model.user.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class InitBinderTest2Controller {

    @InitBinder("user")
    public void init1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("u.");
    }

    @InitBinder("stu")
    public void init2(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("s.");
    }

    /**
     * http://localhost/it2?u.name=%E5%B0%8F%E6%98%8E&u.age=12&s.name=%E5%B0%8F%E5%BC%BA&s.grade=3
     */
    @RequestMapping("/it2")
    public String testBean(User user, @ModelAttribute("stu") Student stu) {
        System.out.println(stu);
        System.out.println(user);
        return user.toString() +System.lineSeparator()+ stu.toString();

    }

    /**
     * 嵌套属性org,使用@RequestBody直接支持。
     * 不需要使用 @InitBinder
     */
    @RequestMapping("/it21")
    public String testBean(@RequestBody User u) {
        System.out.println(u);
        return u.toString();

    }
}
