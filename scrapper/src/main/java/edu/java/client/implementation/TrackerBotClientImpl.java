package edu.java.client.implementation;

import edu.java.client.TrackerBotClient;
import edu.java.client.dto.LinkUpdateRequestDto;
import edu.java.client.exception.BadRequestException;
import edu.java.controller.dto.ApiErrorResponseDto;
import edu.java.model.Link;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TrackerBotClientImpl implements TrackerBotClient {
    private final WebClient webClient;

    public TrackerBotClientImpl(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public void sendUpdate(Link link, String description, List<Long> chatIds) {
        webClient
            .post()
            .uri("/updates")
            .bodyValue(new LinkUpdateRequestDto(link.id(), link.url(), description, chatIds))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::isSameCodeAs, clientResponse ->
                clientResponse.bodyToMono(ApiErrorResponseDto.class)
                    .flatMap(apiErrorResponse -> Mono.error(new BadRequestException(apiErrorResponse)))
            )
            .bodyToMono(Void.class)
            .block();
    }
}
