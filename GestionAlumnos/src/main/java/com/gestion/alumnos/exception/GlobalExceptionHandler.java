package com.gestion.alumnos.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.gestion.alumnos.web.dto.ApiResponseError;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)

    public Mono<ResponseEntity<ApiResponseError>> handleValidationException(WebExchangeBindException ex) {

        List<String> messages = ex.getFieldErrors().stream()
                .map(err -> err.getDefaultMessage())
                .toList();

        ApiResponseError body;

        body = new ApiResponseError(messages);

        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body));
    }

    @ExceptionHandler(AlumnoAlreadyExistsException.class)
    public Mono<ResponseEntity<ApiResponseError>> handleAlumnoAlreadyExists(AlumnoAlreadyExistsException ex) {

        ApiResponseError body = new ApiResponseError(ex.getMessage());

        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(body));
    }

}