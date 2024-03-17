package edu.java.client.implementation;

import edu.java.client.GithubClient;
import edu.java.client.dto.GithubRepositoryRequestDto;
import edu.java.client.dto.GithubRepositoryResponseDto;
import java.util.Optional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GithubClientImpl implements GithubClient {
    private final WebClient webClient;
    private final static String URI_PATTERN = "/repos/{owner}/{repo}";

    public GithubClientImpl(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Optional<GithubRepositoryResponseDto> fetchRepository(GithubRepositoryRequestDto request) {
        return Optional.ofNullable(webClient.get()
            .uri(URI_PATTERN, request.owner(), request.repo())
            .retrieve()
            .bodyToMono(GithubRepositoryResponseDto.class)
            .block());
    }

    @Override
    public boolean exists(GithubRepositoryRequestDto request) {
        return Boolean.TRUE.equals(webClient.get()
            .uri(URI_PATTERN, request.owner(), request.repo())
            .exchangeToMono(response -> {
                if (response.statusCode().is4xxClientError()) {
                    return Mono.just(false);
                } else {
                    return Mono.just(true);
                }
            })
            .block());
    }
}
