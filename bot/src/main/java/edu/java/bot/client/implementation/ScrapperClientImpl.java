package edu.java.bot.client.implementation;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.dto.AddLinkRequestDto;
import edu.java.bot.client.dto.ApiErrorResponseDto;
import edu.java.bot.client.dto.LinkResponseDto;
import edu.java.bot.client.dto.ListLinksResponseDto;
import edu.java.bot.client.dto.RemoveLinkRequestDto;
import edu.java.bot.client.exception.BadRequestException;
import edu.java.bot.client.exception.ConflictException;
import edu.java.bot.client.exception.NotFoundException;
import java.util.function.Function;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClientImpl implements ScrapperClient {
    private final WebClient webClient;
    private static final String LINKS_API_URL = "/links";
    private static final String CHAT_API_URL = "/tg-chat";
    private static final String CHAT_HEADER = "Tg-Chat-Id";

    public ScrapperClientImpl(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public ListLinksResponseDto fetchLinks(long chatId) {
        return webClient
            .get()
            .uri(LINKS_API_URL)
            .header(CHAT_HEADER, Long.toString(chatId))
            .retrieve()
            .onStatus(HttpStatus.NOT_FOUND::isSameCodeAs, clientResponse ->
                handleStatusCode(clientResponse, NotFoundException::new)
            )
            .bodyToMono(ListLinksResponseDto.class)
            .block();
    }

    @Override
    public void registerChat(long chatId) {
        webClient
            .post()
            .uri(CHAT_API_URL + "/" + chatId)
            .retrieve()
            .onStatus(HttpStatus.CONFLICT::isSameCodeAs, clientResponse ->
                handleStatusCode(clientResponse, ConflictException::new)
            )
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public LinkResponseDto trackLink(long chatId, String link) {
        return webClient
            .post()
            .uri(LINKS_API_URL)
            .header(CHAT_HEADER, Long.toString(chatId))
            .bodyValue(new AddLinkRequestDto(link))
            .retrieve()
            .onStatus(HttpStatus.CONFLICT::isSameCodeAs, clientResponse ->
                handleStatusCode(clientResponse, ConflictException::new)
            )
            .onStatus(HttpStatus.NOT_FOUND::isSameCodeAs, clientResponse ->
                handleStatusCode(clientResponse, NotFoundException::new)
            )
            .onStatus(HttpStatus.BAD_REQUEST::isSameCodeAs, clientResponse ->
                handleStatusCode(clientResponse, BadRequestException::new)
            )
            .bodyToMono(LinkResponseDto.class)
            .block();
    }

    @Override
    public LinkResponseDto untrackLink(long chatId, String link) {
        return webClient
            .method(HttpMethod.DELETE)
            .uri(LINKS_API_URL)
            .header(CHAT_HEADER, Long.toString(chatId))
            .bodyValue(new RemoveLinkRequestDto(link))
            .retrieve()
            .onStatus(HttpStatus.NOT_FOUND::isSameCodeAs, clientResponse ->
                handleStatusCode(clientResponse, NotFoundException::new)
            )
            .onStatus(HttpStatus.BAD_REQUEST::isSameCodeAs, clientResponse ->
                handleStatusCode(clientResponse, BadRequestException::new)
            )
            .bodyToMono(LinkResponseDto.class)
            .block();
    }

    private Mono<Exception> handleStatusCode(
        ClientResponse clientResponse,
        Function<ApiErrorResponseDto, Exception> constructor
    ) {
        return clientResponse.bodyToMono(ApiErrorResponseDto.class)
            .flatMap(apiErrorResponse -> Mono.error(constructor.apply(apiErrorResponse)));
    }
}
