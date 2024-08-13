package com.example.demo.service.user;

import com.example.demo.mapper.user.TestMapper;
import com.example.demo.util.SpringContextHolder;
import com.example.demo.util.Tutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<Map<String, Object>> queryOthers() {
        return testMapper.queryOthers();
    }

    public List<Map<String, Object>> queryTb1() {
        return testMapper.queryTb1();
    }

    public int addOrders(Map<String, String> params) {
        return testMapper.addOrders(params);
    }

    @Async
    public void syncTest() {
        System.out.println("start");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");

    }

    /**
     *
     * @Async异步，返回值无效
     * @return java.lang.String
     */
    @Async
    public String syncTest1() {
        System.out.println("start");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
        return "okk";
    }

    /**
     * 事务测试
     */
    @Transactional
    public void ts1() {
        String random = Tutil.getRandom(16);
        Map<String,String> params = new HashMap<String,String>();
        params.put("userid", "xiaoming1_"+random);
        params.put("id", random);
        testMapper.addOrders(params);
        TestService bean = SpringContextHolder.getBean(TestService.class);
        bean.test2();
//        test2();
    }

    @Transactional
    public void test2() {
        String random = Tutil.getRandom(16);
        Map<String,String> params = new HashMap<String,String>();
        params.put("userid", "xiaoming21_"+random);
        params.put("id", random);
        testMapper.addOrders(params);

        Map<String,String> params2 = new HashMap<String,String>();
        params2.put("userid", "xiaoming22_"+random);
        params2.put("id", random);
        int a = 8/0;
        testMapper.addOrders(params);
    }
}
