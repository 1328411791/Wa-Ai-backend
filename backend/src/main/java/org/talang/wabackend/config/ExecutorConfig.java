package org.talang.wabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean("threadPoolTaskExecutor")
    public Executor createExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);//核心线程数
        threadPoolTaskExecutor.setMaxPoolSize(5);//最大线程数
        threadPoolTaskExecutor.setKeepAliveSeconds(60);//空闲时间
        threadPoolTaskExecutor.setQueueCapacity(10);//等待队列
        threadPoolTaskExecutor.setThreadNamePrefix("thread");//线程名前缀
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//拒绝策略
        return threadPoolTaskExecutor;
    }
}
