package com.github.rich.log;

import com.github.rich.log.aop.UserLogAop;
import com.github.rich.log.service.OperateLogService;
import com.github.rich.log.service.impl.OperateLogServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志自动配置
 * @author Petty
 * @date 2019-11-26
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration {

	@Bean
	public OperateLogService operateLogService(){
		return new OperateLogServiceImpl();
	}

	@Bean
	public UserLogAop userOperateLogAop(){
		return new UserLogAop(operateLogService());
	}

}
