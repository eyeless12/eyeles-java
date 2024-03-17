package edu.java.client.implementation;

import edu.java.client.StackOverflowClient;
import edu.java.client.dto.StackOverflowPostInnerResponseDto;
import edu.java.client.dto.StackOverflowPostResponseDto;
import java.util.Optional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient webClient;
    private static final String URI_PATTERN = "/posts/{postId}?site=stackoverflow&filter=!nNPvSNOTRz";

    public StackOverflowClientImpl(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Optional<StackOverflowPostInnerResponseDto> fetchPost(long postId) {
        Optional<StackOverflowPostResponseDto> resp = webClient.get()
            .uri(URI_PATTERN, postId)
            .exchangeToMono(response -> {
                if (response.statusCode().is4xxClientError()) {
                    return Mono.just(Optional.<StackOverflowPostResponseDto>empty());
                }
                return response.bodyToMono(StackOverflowPostResponseDto.class).flatMap(r -> Mono.just(Optional.of(r)));
            })
            .block();
        if (resp.isEmpty() || resp.isPresent() && resp.get().items().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(resp.get().items().getFirst());
    }

    @Override
    public boolean exists(long postId) {
        return Boolean.TRUE.equals(webClient.get()
            .uri(URI_PATTERN, postId)
            .retrieve()
            .bodyToMono(StackOverflowPostResponseDto.class)
            .flatMap(response -> {
                if (response != null && !response.items().isEmpty()) {
                    return Mono.just(true);
                }
                return Mono.just(false);
            }).block());
    }
}
