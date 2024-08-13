package com.example.demo.controller.user;

import com.example.demo.common.convert.TestEntity;
import com.example.demo.common.param.MyParam;
import com.example.demo.service.user.TestService;
import com.example.demo.util.Tutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;

    /**
     * 测试字符串参数转list
     * http://localhost:66/test/t2?p1=a&p2=2&p3=a,b,c&p4=qwe&p5=qwe
     */
    @RequestMapping("/t2")
    public TestEntity t2(HttpServletRequest request, TestEntity testEntity){
        System.out.println(testEntity);
        return testEntity;
    }

    /**
     * 和@RequestParam功能上是并列的，如果date类型，使用了RequestParam，日期格式要注意，最好不要再加RequestParam
     */
    @RequestMapping("/t1")
    public String t1(HttpServletRequest request, @MyParam String s1, @MyParam() String p1, @MyParam() Date d1){
        System.out.println(s1);
        System.out.println(p1);
        System.out.println(d1);
        return s1+"-------"+p1;
    }

    /**
     * 事务测试
     */
    @RequestMapping("/ts1")
    public String t1(HttpServletRequest request){
        testService.ts1();
        return "ok";
    }

    @RequestMapping("/sync")
    public String syncTest(HttpServletRequest request){
        testService.syncTest();
        return "ok";
    }

    @RequestMapping("/sync1")
    public String syncTest1(HttpServletRequest request){
        //syncTest1方法使用了@Async注解，不能接收到返回值
        String s = testService.syncTest1();
        System.out.println("async 结果："+s);
        return "ok";
    }

    @RequestMapping("/tb1")
    public List<Map<String, Object>> queryTb1(HttpServletRequest request){
        return testService.queryTb1();
    }

    /**
     * 其他查询
     */
    @RequestMapping("/other/query")
    public List<Map<String, Object>> query(HttpServletRequest request){
        return testService.queryOthers();
    }

    /**
     * 其他查询
     */
    @RequestMapping("/addOrders")
    public int addOrders(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", request.getParameter("id"));
        params.put("userid", Tutil.getRandom(16));
        return testService.addOrders(params);
    }

}
