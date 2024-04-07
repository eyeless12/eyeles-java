package edu.java.configuration.repository;

import edu.java.client.GithubClient;
import edu.java.client.StackOverflowClient;
import edu.java.configuration.props.ApplicationConfig;
import edu.java.gateway.UpdatesGateway;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.jdbc.implementation.JdbcChatRepositoryImpl;
import edu.java.repository.jdbc.implementation.JdbcLinkRepositoryImpl;
import edu.java.service.ChatService;
import edu.java.service.DomainService;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdaterService;
import edu.java.service.domains.jdbc.JdbcDomain;
import edu.java.service.domains.jdbc.JdbcGithubDomain;
import edu.java.service.domains.jdbc.JdbcStackOverflowDomain;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcLinkUpdaterService;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcChatRepository chatRepository(DataSource dataSource) {
        return new JdbcChatRepositoryImpl(dataSource);
    }

    @Bean
    public JdbcLinkRepository linkRepository(DataSource dataSource) {
        return new JdbcLinkRepositoryImpl(dataSource);
    }

    @Bean
    public List<JdbcDomain> domains(
        UpdatesGateway updatesGateway,
        GithubClient githubClient,
        StackOverflowClient stackOverflowClient,
        JdbcChatRepository chatRepository
    ) {
        return List.of(
            new JdbcGithubDomain(updatesGateway, githubClient, chatRepository),
            new JdbcStackOverflowDomain(updatesGateway, stackOverflowClient, chatRepository)
        );
    }

    @Bean
    public DomainService domainService(List<JdbcDomain> domains) {
        return new DomainService(domains);
    }

    @Bean
    public LinkService linkService(
        JdbcChatRepository chatRepository,
        JdbcLinkRepository linkRepository,
        DomainService domainService
    ) {
        return new JdbcLinkService(chatRepository, linkRepository, domainService);
    }

    @Bean
    public ChatService chatService(JdbcChatRepository chatRepository) {
        return new JdbcChatService(chatRepository);
    }

    @Bean
    public LinkUpdaterService linkUpdaterService(
        JdbcLinkRepository linkRepository,
        ApplicationConfig applicationConfig,
        List<JdbcDomain> domains
    ) {
        return new JdbcLinkUpdaterService(linkRepository, applicationConfig, domains);
    }
}
