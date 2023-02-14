package com.ssafy.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleConifg implements AsyncConfigurer, SchedulingConfigurer{

	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(Runtime.getRuntime().availableProcessors()*2);
		scheduler.setThreadNamePrefix("LEAVE-ROOM-SCHEDULER-");
		scheduler.initialize();
		return scheduler;
	}
		
	
	//scheduler가 async하게 동작하도록 등록
	@Override
	public Executor getAsyncExecutor() {
		return this.threadPoolTaskScheduler();
	}

	//threadpoolscheduler 등록
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(this.threadPoolTaskScheduler());
		
	}

}
