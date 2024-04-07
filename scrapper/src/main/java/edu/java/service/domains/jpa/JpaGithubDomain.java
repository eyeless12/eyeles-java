package edu.java.service.domains.jpa;

import edu.java.client.GithubClient;
import edu.java.gateway.UpdatesGateway;
import edu.java.gateway.dto.LinkUpdate;
import edu.java.service.domains.GithubDomain;
import edu.java.service.model.jpa.JpaChat;
import edu.java.service.model.jpa.JpaLink;
import edu.java.util.CommonUtils;
import java.net.URL;

public class JpaGithubDomain extends GithubDomain implements JpaDomain {
    private final UpdatesGateway updatesGateway;

    public JpaGithubDomain(GithubClient githubClient, UpdatesGateway updatesGateway) {
        super(githubClient);
        this.updatesGateway = updatesGateway;
    }

    @Override
    public void update(JpaLink link) {
        URL parsed = CommonUtils.toURL(link.getUrl());
        githubClient.fetchRepository(toGithubRepository(parsed))
            .ifPresent((githubResponse -> {
                if (githubResponse.lastActivityDate().isAfter(link.getLastCheckTime())) {
                    updatesGateway.sendUpdate(new LinkUpdate(
                        link.getId(),
                        link.getUrl(),
                        createDescription(githubResponse),
                        link.getChats().stream().map(JpaChat::getId).toList()
                    ));
                }
            }));
    }
}
