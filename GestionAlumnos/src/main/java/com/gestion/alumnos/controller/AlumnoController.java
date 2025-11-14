package com.gestion.alumnos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.gestion.alumnos.domain.Alumno;

import com.gestion.alumnos.service.AlumnoService;
import com.gestion.alumnos.web.dto.AlumnoRequest;
import com.gestion.alumnos.web.dto.AlumnoResponse;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/v1/alumnos")
@RestController

public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @PostMapping("/crear")
    public Mono<ResponseEntity<Object>> crear(@Valid @RequestBody AlumnoRequest request) {
        return alumnoService.crearAlumno(request)
                .then(Mono.just(ResponseEntity.ok().<Object>build()));
    }

    @GetMapping
    public Flux<AlumnoResponse> findAlumnosActives() {
        return alumnoService.obtenerAlumnosActivos()
                .map(this::mapToResponse);
    }

    private AlumnoResponse mapToResponse(Alumno alumno) {
        return new AlumnoResponse(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getEstado(),
                alumno.getEdad());
    }

}