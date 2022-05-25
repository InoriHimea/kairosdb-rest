package org.inori.rest.kairosdbrest.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/25 15:17 5月
 * @since 1.8
 */
@Configuration
@Log4j2
public class KafkaConfig {

    @Bean("kafkaConsumerTaskExecutor")
    public AsyncListenableTaskExecutor kafkaTaskBean() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("");
        executor.setThreadGroupName("");
        executor.setDaemon(true);
        executor.setQueueCapacity(20);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setPrestartAllCoreThreads(true);
        executor.setMaxPoolSize(20);
        executor.setCorePoolSize(10);
        executor.setBeanName("kafkaConsumerTaskExecutor");
        return executor;
    }
}
