package com.example.demo.common.threadpool.context;

import com.example.demo.common.threadpool.ThreadPoolConstant;
import com.example.demo.common.threadpool.properties.Config;
import com.example.demo.common.threadpool.properties.Pool;
import com.example.demo.common.threadpool.properties.ThreadPoolProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自动注册线程池
 * @author jiangbaojun
 * @date 2023/4/10 16:14
 */
@Configuration
public class ThreadPoolRegistryPostProcessor implements EnvironmentAware, BeanDefinitionRegistryPostProcessor {

	private Environment environment;

    /**
     * 注册线程
     * @date 2023/4/10 16:14
     */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		BindResult<ThreadPoolProperties> restServiceBindResult = Binder.get(environment).bind(ThreadPoolConstant.CONFIG_PREFIX, ThreadPoolProperties.class);
		ThreadPoolProperties threadPoolProperties = restServiceBindResult.get();
		if(threadPoolProperties==null || !threadPoolProperties.getEnable()){
			return;
		}
		Map<String, Pool> pools = threadPoolProperties.getPools();
		if(pools==null || pools.size()==0){
			return;
		}
		boolean defaultBeforeClear = threadPoolProperties.getBeforeClear();
		for (Map.Entry<String, Pool> entry : pools.entrySet()) {
			String beanName = entry.getKey();
			Pool pool = entry.getValue();
			boolean beforeClear = pool.getBeforeClear()==null?defaultBeforeClear:pool.getBeforeClear();
			String threadGroupName = pool.getThreadGroupName()==null?beanName:pool.getThreadGroupName();
			Config config = pool.getConfig();
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
					.genericBeanDefinition(ContextThreadPoolExecutor.class);
			beanDefinitionBuilder.addConstructorArgValue(config.getCoreSize())
					.addConstructorArgValue(config.getMaxSize())
					.addConstructorArgValue(config.getKeepAlive().getSeconds())
					.addConstructorArgValue(TimeUnit.SECONDS)
					.addConstructorArgValue(new LinkedBlockingQueue<>(config.getQueueCapacity()))
					.addConstructorArgValue(new ContextThreadFactory(new ThreadGroup(threadGroupName)))
					.addConstructorArgValue(new ThreadPoolExecutor.AbortPolicy())
					.addConstructorArgValue(beforeClear);
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