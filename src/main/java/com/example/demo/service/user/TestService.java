package com.example.demo.service.user;

import com.example.demo.mapper.user.TestMapper;
import com.example.demo.mapper.user.UserMapper;
import com.example.demo.model.user.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
}
