package com.example.demo.controller.user;

import com.example.demo.model.user.User;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model){
        model.addAttribute("userContent",123);
        model.addAttribute("userContentHello","你好");
        return "user/user";
    }
    /**
     * 不分页查询
     * @param request
     * @return
     */
    @RequestMapping("/all")
    @ResponseBody
    public List<User> test1(HttpServletRequest request){
        return userService.findByUsersAll();
    }

    /**
     * 单条操作
     */
    @RequestMapping("/one")
    @ResponseBody
    public String one(HttpServletRequest request){
        userService.insertOne(request.getParameter("name"));
        return "complete";
    }

    /**
     * 批量操作
     */
    @RequestMapping("/batch")
    @ResponseBody
    public String batch(HttpServletRequest request){
        userService.batch();
        return "complete";
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public List<User> test2(HttpServletRequest request){
        return userService.findByUsersPage().getList();
    }
}
