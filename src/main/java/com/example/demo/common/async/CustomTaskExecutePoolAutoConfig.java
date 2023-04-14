package com.example.demo.common.async;

import com.example.demo.common.async.properties.AsyncPool;
import com.example.demo.common.async.properties.AsyncProperties;
import com.example.demo.common.async.thread.MyThreadPoolTaskExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 自动创建自定义的ThreadPoolTaskExecutor
 * @author jiangbaojun
 * @date 2023/4/11 15:32
 */
@Configuration
public class CustomTaskExecutePoolAutoConfig implements EnvironmentAware, BeanDefinitionRegistryPostProcessor {

    private Environment environment;
    /**
     * 注册自定义的ThreadPoolTaskExecutor
     * @date 2023/4/11 15:32
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BindResult<AsyncProperties> restServiceBindResult = Binder.get(environment).bind("com.mrk.async", AsyncProperties.class);
        BindResult<TaskExecutionProperties> restServiceBindResultDefault = Binder.get(environment).bind("spring.task.execution", TaskExecutionProperties.class);
        AsyncProperties asyncProperties;
        try {
            asyncProperties = restServiceBindResult.get();
        } catch (NoSuchElementException e) {
            System.out.println("com.mrk.async not config");
            asyncProperties = new AsyncProperties();
        }
        TaskExecutionProperties properties;
        try {
            properties = restServiceBindResultDefault.get();
        } catch (NoSuchElementException e) {
            properties = new TaskExecutionProperties();
        }

        if(asyncProperties==null || !asyncProperties.getAutoCreateCustomer()){
            return;
        }
        Map<String, AsyncPool> pools = asyncProperties.getConfig();
        if(pools==null || pools.size()==0){
            return;
        }
        for (Map.Entry<String, AsyncPool> entry : pools.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                    .genericBeanDefinition(MyThreadPoolTaskExecutor.class);
            beanDefinitionBuilder.addConstructorArgValue(properties)
                    .addConstructorArgValue(asyncProperties)
                    .addConstructorArgValue(beanName);
            BeanDefinition personManagerBeanDefinition = beanDefinitionBuilder
                    .getRawBeanDefinition();
            registry.registerBeanDefinition(beanName, personManagerBeanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}