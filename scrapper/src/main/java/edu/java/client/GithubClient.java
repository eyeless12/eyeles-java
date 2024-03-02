package edu.java.client;

import edu.java.client.dto.GithubRepositoryResponseDto;

public interface GithubClient {
    GithubRepositoryResponseDto fetchRepository(String owner, String repo);
}
