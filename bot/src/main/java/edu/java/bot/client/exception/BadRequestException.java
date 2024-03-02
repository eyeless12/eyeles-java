package edu.java.bot.client.exception;

import edu.java.bot.client.dto.ApiErrorResponseDto;

public class BadRequestException extends ApiErrorResponseException {

    public BadRequestException(ApiErrorResponseDto apiErrorResponse) {
        super(apiErrorResponse);
    }
}
