package com.example.demo.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.user.User;

@Mapper
public interface UserMapper {
    List<User> findByUsers();

    List<Map<String, Object>> queryOthers();

    void insertOne(User user);
    void batchTest1(List<User> list);
    void batchTest2(List<User> list);
}
