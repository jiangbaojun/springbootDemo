package com.example.demo.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "操作接口")
@RestController
@RequestMapping("/api/v1/test")
public class TestApi {

    @ApiOperation("添加数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名字", dataType = "String", required = true),
            @ApiImplicitParam(name = "typeId", value = "类型ID", dataType = "Long", required = true)
    })
    @PutMapping("add")
    public String add(String name, Long typeId){
        return "added";
    }

    @ApiOperation("获取数据")
    @GetMapping("get")
    public String get(@ApiParam(name = "id", value = "数据ID") Long id){
        return "小明";
    }
}
