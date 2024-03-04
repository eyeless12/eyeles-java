package edu.java.bot.client.exception;

import edu.java.bot.client.dto.ApiErrorResponseDto;

public class NotFoundException extends ApiErrorResponseException {
    public NotFoundException(ApiErrorResponseDto apiErrorResponse) {
        super(apiErrorResponse);
    }
}
