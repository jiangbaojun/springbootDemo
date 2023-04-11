package com.example.demo.common.threadpool.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserExt implements Serializable {
    private Integer userId;
    private Integer userType;
    private Integer systemId;

}
