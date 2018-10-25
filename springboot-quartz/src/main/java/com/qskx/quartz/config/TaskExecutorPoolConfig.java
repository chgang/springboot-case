package com.qskx.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ProjectName: p2passet-batch-call
 * @Package: com.fengjr.p2passetbatchcall.conf
 * @ClassName: TaskExecutorConfig
 * @Description: java类作用描述
 * @Author: 111111
 * @CreateDate: 2018/10/24 18:18
 * @Version: 1.0
 * Copyright: Copyright (c) 2018
 */
@Configuration
public class TaskExecutorPoolConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
