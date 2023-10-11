package com.example.demo.common.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义conditional配置
 *
 */
@Configuration
public class TestConditionalConfig {

    @Bean("tm1")
    @MyConditionAnnotation(key = "a.b.c", values = {"a","b"})
    public TestModel tm1() {
        return new TestModel("tm1", 11);
    }

    @Bean("tm2")
    @MyConditionAnnotation(key = "x.y.z", values = {"x","y"})
    public TestModel tm2() {
        return new TestModel("tm2", 12);
    }

    @Bean("tm3")
    @ConditionalOnProperty(name = "m.n")
    public TestModel tm3() {
        return new TestModel("tm3", 12);
    }
}