package com.example.demo.controller.test;

import com.example.demo.model.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * thymeleaf模板测试
 * @author 姜宝俊
 * @date 2018/12/21 9:27
 */
@Controller
@RequestMapping(value = "/return")
public class ThlController {
    /**
     * Model本身不能设置页面跳转的url地址别名或者物理跳转地址，那么我们可以通过控制器方法的返回值来设置跳转url
     * 地址别名或者物理跳转地址
     *
     * @param model
     *            一个接口， 其实现类为ExtendedModelMap，继承了ModelMap类
     * @return 跳转url地址别名或者物理跳转地址
     */
    @RequestMapping(value = "/index1")
    public String index1(Model model) {
        model.addAttribute("result", "后台返回index1");
        return "thymeleafDemo";
    }

    /**
     * ModelMap对象主要用于传递控制方法处理数据到结果页面,类似于request对象的setAttribute方法的作用。 用法等同于Model
     *
     * @param model
     * @return 跳转url地址别名或者物理跳转地址
     */
    @RequestMapping(value = "/index2")
    public String index2(ModelMap model, HttpServletRequest request, HttpSession session) {
        User u = new User("1111","小强",12,new Date());
        model.addAttribute("result", "后台返回index2");
        model.addAttribute("user", u);
        request.setAttribute("u",u);

        request.setAttribute("name",1112);
        session.setAttribute("name",33334);

        List<User> list = new ArrayList<User>();
        list.add(new User("11111","小强1",11,new Date()));
        list.add(new User("11112","小强2",12,new Date()));
        list.add(new User("11113","小强3",13,new Date()));
        list.add(new User("11114","小强4",14,new Date()));
        model.addAttribute("userList", list);

        return "thymeleafDemo";
    }

    /**
     * ModelAndView主要有两个作用 设置转向地址和传递控制方法处理结果数据到结果页面
     * @return 返回一个模板视图对象
     */
    @RequestMapping(value = "/index3")
    public ModelAndView index3() {
        ModelAndView mv = new ModelAndView("thymeleafDemo");
        // ModelAndView mv = new ModelAndView();
        // mv.setViewName("result");
        mv.addObject("result", "后台返回值index3");
        return mv;
    }

    /**
     * map用来存储递控制方法处理结果数据，通过ModelAndView方法返回。
     * 当然Model,ModelMap也可以通过这种方法返回
     * @return 返回一个模板视图对象
     */
    @RequestMapping(value = "/index4")
    public ModelAndView index4() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("result", "后台返回index4");
        return new ModelAndView("thymeleafDemo", map);
    }

    @RequestMapping(value = "/hrefTest")
    @ResponseBody
    public String hrefTest(ModelMap model, HttpServletRequest request, HttpSession session) {
        String s1 = request.getParameter("param1");
        String s2 = request.getParameter("param2");
        return s1+"-"+s2;
    }
}
