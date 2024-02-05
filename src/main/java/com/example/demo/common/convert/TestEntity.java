package com.example.demo.common.convert;

import lombok.Data;

import java.util.List;

@Data
public class TestEntity {

    private String p1;
    private Integer p2;
    @MyConvertAnnotation()
    private List<String> p3;
    private List<String> p4;
    private List<Object> p5;

}
