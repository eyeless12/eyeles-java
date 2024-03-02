package edu.java.bot.client.exception;

import edu.java.bot.client.dto.ApiErrorResponseDto;

public class ConflictException extends ApiErrorResponseException {
    public ConflictException(ApiErrorResponseDto apiErrorResponse) {
        super(apiErrorResponse);
    }
}
