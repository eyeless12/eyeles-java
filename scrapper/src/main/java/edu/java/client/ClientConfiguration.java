package edu.java.client;

import edu.java.client.implementation.GithubClientImpl;
import edu.java.client.implementation.StackOverflowClientImpl;
import edu.java.configuration.props.ApplicationConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class ClientConfiguration {
    @Bean
    public GithubClient githubClient(
        ApplicationConfig applicationConfig, @Qualifier("githubRetryTemplate") RetryTemplate retryTemplate
    ) {
        return new GithubClientImpl(applicationConfig.clients().github().baseUrl(), retryTemplate);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(
        ApplicationConfig applicationConfig, @Qualifier("stackOverflowRetryTemplate") RetryTemplate retryTemplate
    ) {
        return new StackOverflowClientImpl(applicationConfig.clients().stackOverflow().baseUrl(), retryTemplate);
    }
}
