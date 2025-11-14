package com.gestion.alumnos.service;
import com.gestion.alumnos.domain.Alumno;
import com.gestion.alumnos.web.dto.AlumnoRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoService {

    Mono<Alumno> crearAlumno(AlumnoRequest request);

    Flux<Alumno> obtenerAlumnosActivos();
}