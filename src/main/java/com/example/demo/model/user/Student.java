package com.example.demo.model.user;


import java.util.Date;

/**
 * 用户实体
 */
public class Student {

    private String id;
    private String name;
    private Integer age;
    private Date birthday;
    private String grade;

    public Student(String id, String name, Integer age, Date birthday, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.grade = grade;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", grade='" + grade + '\'' +
                '}';
    }
}
