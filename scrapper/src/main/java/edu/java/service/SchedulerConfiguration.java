package edu.java.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {
    @Bean
    public LinkUpdaterScheduler linkUpdaterScheduler(ILinkUpdaterService linkUpdaterService) {
        return new LinkUpdaterScheduler(linkUpdaterService);
    }
}
