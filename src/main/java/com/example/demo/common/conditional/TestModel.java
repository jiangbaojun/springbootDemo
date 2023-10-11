package com.example.demo.common.conditional;

public class TestModel {
    private String name;
    private Integer age;

    public TestModel(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public TestModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
