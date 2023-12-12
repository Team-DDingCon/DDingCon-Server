package com.pocalink.ddingcon.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        HttpStatus status = exception.getErrorCode().getStatus();
        return ResponseEntity.status(status).body(ErrorResponse.of(status.value(), exception.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleServerErrorException(Exception exception) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ErrorResponse.of(500, "서버 내부 오류가 발생했습니다."));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        return ResponseEntity.status(statusCode)
                .body(ErrorResponse.of(statusCode.value(), exception.getMessage()));
    }
}
