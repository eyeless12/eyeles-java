package edu.java.client;

import edu.java.client.dto.GithubRepositoryRequestDto;
import edu.java.client.dto.GithubRepositoryResponseDto;
import java.util.Optional;

public interface GithubClient {
    Optional<GithubRepositoryResponseDto> fetchRepository(GithubRepositoryRequestDto githubRepositoryRequest);

    boolean exists(GithubRepositoryRequestDto githubRepositoryRequest);
}
