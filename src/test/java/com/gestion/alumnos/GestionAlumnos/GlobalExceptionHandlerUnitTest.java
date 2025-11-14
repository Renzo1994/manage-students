package com.gestion.alumnos.GestionAlumnos;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.gestion.alumnos.exception.AlumnoAlreadyExistsException;
import com.gestion.alumnos.exception.GlobalExceptionHandler;
import com.gestion.alumnos.web.dto.ApiResponseError;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

public class GlobalExceptionHandlerUnitTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleAlumnoAlreadyExists() {
        AlumnoAlreadyExistsException ex = new AlumnoAlreadyExistsException("Alumno repetido");

        Mono<ResponseEntity<ApiResponseError>> result = handler.handleAlumnoAlreadyExists(ex);

        StepVerifier.create(result)
                .assertNext(resp -> {
                    assert resp.getStatusCode() == HttpStatus.CONFLICT;
                    assert resp.getBody().getMessages().equals(List.of("Alumno repetido"));
                })
                .verifyComplete();
    }
}