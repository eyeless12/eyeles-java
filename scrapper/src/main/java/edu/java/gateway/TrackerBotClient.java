package edu.java.gateway;

import edu.java.client.dto.LinkUpdateRequest;
import edu.java.client.exception.BadRequestException;
import edu.java.controller.dto.ApiErrorResponse;
import edu.java.gateway.dto.LinkUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TrackerBotClient implements UpdatesGateway {
    private final RetryTemplate retryTemplate;
    private final WebClient webClient;

    public TrackerBotClient(String baseUrl, RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public void sendUpdate(LinkUpdate linkUpdate) {
        retryTemplate.execute(context -> webClient
            .post()
            .uri("/updates")
            .bodyValue(new LinkUpdateRequest(
                linkUpdate.id(),
                linkUpdate.url(),
                linkUpdate.description(),
                linkUpdate.tgChatIds()
            ))
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::isSameCodeAs, clientResponse ->
                clientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiErrorResponse -> Mono.error(new BadRequestException(apiErrorResponse)))
            )
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.createException().flatMap(Mono::error))
            .bodyToMono(Void.class)
            .block());
    }
}
