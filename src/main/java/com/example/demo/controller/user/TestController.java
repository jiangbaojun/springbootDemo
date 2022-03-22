package com.example.demo.controller.user;

import com.example.demo.service.user.TestService;
import com.example.demo.util.Tutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;

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
