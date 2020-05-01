package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
	
    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){
    	model.addAttribute("content", "Hello Thymeleaf8");
        return "index";
    }
}