package com.example.demo.controller.timezone;

import com.example.demo.model.user.User;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/user/tz")
public class UserTzController {

	@Autowired
	private UserService userService;

    /**
     *
     * http://localhost:66/user/tz/test/deserialize2?birthday=11/05/2011
     */
    @RequestMapping("/test/deserialize2")
    @ResponseBody
    public String test2_1(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date birthday){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(birthday);
    }

    /**
     * 使用DynamicDateDeserialize
     */
    @RequestMapping("/test/deserialize")
    @ResponseBody
    public String test2(@RequestBody User user){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getBirthday());
    }

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
