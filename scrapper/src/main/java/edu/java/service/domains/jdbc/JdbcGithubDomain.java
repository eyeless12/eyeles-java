package edu.java.service.domains.jdbc;

import edu.java.client.GithubClient;
import edu.java.gateway.UpdatesGateway;
import edu.java.gateway.dto.LinkUpdate;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.service.domains.GithubDomain;
import edu.java.service.model.Chat;
import edu.java.service.model.jdbc.JdbcLink;
import edu.java.util.CommonUtils;
import java.net.URL;

public class JdbcGithubDomain extends GithubDomain implements JdbcDomain {
    private final UpdatesGateway updatesGateway;
    private final JdbcChatRepository chatRepository;

    public JdbcGithubDomain(
        UpdatesGateway updatesGateway,
        GithubClient githubClient,
        JdbcChatRepository chatRepository
    ) {
        super(githubClient);
        this.updatesGateway = updatesGateway;
        this.chatRepository = chatRepository;
    }

    @Override
    public void update(JdbcLink link) {
        URL parsed = CommonUtils.toURL(link.getUrl());
        githubClient.fetchRepository(toGithubRepository(parsed))
            .ifPresent((githubResponse -> {
                if (githubResponse.lastActivityDate().isAfter(link.getLastCheckTime())) {
                    updatesGateway.sendUpdate(new LinkUpdate(
                        link.getId(),
                        link.getUrl(),
                        createDescription(githubResponse),
                        chatRepository.findAllByLink(link.getId()).stream().map(Chat::getId).toList()
                    ));
                }
            }));
    }
}
