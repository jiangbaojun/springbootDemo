package com.example.demo.controller.timezone;

import com.example.demo.model.user.User;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/user/tz")
public class UserTzController {

	@Autowired
	private UserService userService;

    @RequestMapping("/all")
    @ResponseBody
    public List<User> test1(HttpServletRequest request){
        List<User> usersAll = userService.findByUsersAll();
        return usersAll;
    }

    @RequestMapping("/test/query/one")
    @ResponseBody
    public User test2(HttpServletRequest request){
        User user = new User();
        user.setName("xiaoming111");
        user.setBirthday(new Date());
        return user;
    }

    /**
     * 单条操作
     */
    @PostMapping("/post/one")
    @ResponseBody
    public String one(@RequestBody User user){
        if(user.getBirthday()==null){
            user.setBirthday(new Date());
        }
        userService.insertOne(user);
        return "complete";
    }

}
