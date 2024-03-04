package edu.java.controller;

import edu.java.controller.dto.ApiErrorResponseDto;
import edu.java.exception.ChatAlreadyRegisteredException;
import edu.java.exception.InvalidLinkException;
import edu.java.exception.LinkAlreadyTrackingException;
import edu.java.exception.NoSuchChatException;
import edu.java.exception.NoSuchLinkException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RestExceptionHandler {
    private ResponseEntity<ApiErrorResponseDto> handleError(Exception ex, HttpStatus status, String description) {
        return ResponseEntity.status(status).body(new ApiErrorResponseDto(
            description,
            Integer.toString(status.value()),
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList()
        ));
    }

    @ExceptionHandler(value = {LinkAlreadyTrackingException.class})
    public ResponseEntity<ApiErrorResponseDto> handleLinkAlreadyTracking(Exception ex) {
        return handleError(ex, HttpStatus.CONFLICT, "Link is already tracking");
    }

    @ExceptionHandler(value = {ChatAlreadyRegisteredException.class})
    public ResponseEntity<ApiErrorResponseDto> handleChatAlreadyRegistered(Exception ex) {
        return handleError(ex, HttpStatus.CONFLICT, "Chat is already registered");
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiErrorResponseDto> handleBadRequest(Exception ex) {
        return handleError(ex, HttpStatus.BAD_REQUEST, "Invalid request parameters");
    }

    @ExceptionHandler(value = {InvalidLinkException.class})
    public ResponseEntity<ApiErrorResponseDto> handleInvalidLink(Exception ex) {
        return handleError(ex, HttpStatus.BAD_REQUEST, "The link is not correct");
    }

    @ExceptionHandler(value = {NoSuchChatException.class})
    public ResponseEntity<ApiErrorResponseDto> handleNotFoundChat(Exception ex) {
        return handleError(ex, HttpStatus.NOT_FOUND, "Chat doesn't exist");
    }

    @ExceptionHandler(value = {NoSuchLinkException.class})
    public ResponseEntity<ApiErrorResponseDto> handleNotFoundLink(Exception ex) {
        return handleError(ex, HttpStatus.NOT_FOUND, "The link is not tracked by this chat");
    }
}
