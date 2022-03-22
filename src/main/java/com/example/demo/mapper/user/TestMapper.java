package com.example.demo.mapper.user;

import com.example.demo.model.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestMapper {

    List<Map<String, Object>> queryOthers();

    List<Map<String, Object>> queryTb1();

    int addOrders(Map<String, String> params);
}