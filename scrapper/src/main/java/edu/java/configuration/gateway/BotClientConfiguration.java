package edu.java.configuration.gateway;

import edu.java.client.retry.RetryConfiguration;
import edu.java.configuration.props.ApplicationConfig;
import edu.java.gateway.TrackerBotClient;
import edu.java.gateway.UpdatesGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class BotClientConfiguration {
    @Bean
    public RetryTemplate trackerBotRetryTemplate(
        ApplicationConfig applicationConfig,
        RetryConfiguration retryConfiguration
    ) {
        return retryConfiguration.createRetryTemplate(applicationConfig.clients().trackerBot().retry());
    }

    @Bean
    public UpdatesGateway trackerBotClient(
        ApplicationConfig applicationConfig, @Qualifier("trackerBotRetryTemplate") RetryTemplate retryTemplate
    ) {
        return new TrackerBotClient(applicationConfig.clients().trackerBot().baseUrl(), retryTemplate);
    }
}
