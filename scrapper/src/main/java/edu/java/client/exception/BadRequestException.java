package edu.java.client.exception;

import edu.java.controller.dto.ApiErrorResponseDto;

public class BadRequestException extends ApiErrorResponseException {
    public BadRequestException(ApiErrorResponseDto apiErrorResponse) {
        super(apiErrorResponse);
    }
}
