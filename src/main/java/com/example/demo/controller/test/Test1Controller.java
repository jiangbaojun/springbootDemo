package com.example.demo.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * @author 姜宝俊
 * @date 2018/12/21 9:27
 */
@RestController
@RequestMapping(value = "/test1")
public class Test1Controller {

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/t1")
    public String t1() {
        String name = environment.getProperty("dept.name");
        String age = environment.getProperty("dept.age");
        String address = environment.getProperty("dept.address");
        String phone = environment.getProperty("dept.phone");
        String email = environment.getProperty("dept.email");
        String dev = environment.getProperty("dept.dev");
        String unKnow = environment.getProperty("not.exist");

        return "ok";
    }

}
