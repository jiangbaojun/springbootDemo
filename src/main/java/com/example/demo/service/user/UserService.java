package com.example.demo.service.user;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.user.UserMapper;
import com.example.demo.model.user.User;

import javax.annotation.Resource;

@Service
public class UserService {
 
    @Resource
    private UserMapper userMapper;

    /**
     * 获得所有数据
     * @return
     */
    public PageInfo<User> findByUsersPage() {
        PageHelper.startPage(1,10);
        List<User> list = userMapper.findByUsers();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }

    /**
     * 获得分页数据
     */
    public List<User> findByUsersAll() {
        return userMapper.findByUsers();
    }
}