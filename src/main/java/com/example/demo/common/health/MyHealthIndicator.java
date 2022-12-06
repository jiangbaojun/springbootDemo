package com.example.demo.common.health;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义健康状态检查。检查名称根据类命名规则，名称+HealthIndicator，本例：my
 * http://localhost:88/actuator/health
 * 开启端点：management.endpoints.web.exposure.include='*'
 * 显示详细信息：management.endpoint.health.show-details=always
 * @author jiangbaojun
 * @version v1.0
 */
@Component
public class MyHealthIndicator extends AbstractHealthIndicator {

	private static final Log logger = LogFactory.getLog(MyHealthIndicator.class);

	AtomicInteger atomicInteger = new AtomicInteger();

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		int i = atomicInteger.addAndGet(1);
		//此处仅仅是模拟正常和异常的输出。实际上判断组件的状态，例如jdbc、redis等。
		if(i%3==0){
			builder.down()
					.withDetail("error", "test error")
					.withDetail("error", "test error")
					.withDetail("detail", "modulo zero");
		}else{
			builder.up()
					.withDetail("status", "ok");
		}
	}

}
