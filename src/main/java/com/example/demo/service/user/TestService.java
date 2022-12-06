package com.example.demo.service.user;

import com.example.demo.mapper.user.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
}
