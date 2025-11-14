package com.gestion.alumnos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.gestion.alumnos.domain.Alumno;

import reactor.core.publisher.Flux;

public interface AlumnoRepository extends ReactiveCrudRepository<Alumno, Long> {

    Flux<Alumno> findByEstado(String estado);
}