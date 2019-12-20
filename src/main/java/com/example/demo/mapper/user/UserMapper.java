package com.example.demo.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.user.User;

@Mapper
public interface UserMapper {
    List<User> findByUsers();
}