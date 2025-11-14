package com.gestion.alumnos.service.impl;

import org.springframework.stereotype.Service;

import com.gestion.alumnos.constants.AlumnoConstants;
import com.gestion.alumnos.domain.Alumno;
import com.gestion.alumnos.repository.AlumnoRepository;
import com.gestion.alumnos.service.AlumnoService;
import com.gestion.alumnos.web.dto.AlumnoRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlumnoServiceImpl  implements AlumnoService{
    private final AlumnoConstants constants;
    private final AlumnoRepository alumnoRepository;
    public AlumnoServiceImpl (AlumnoRepository alumnoRepository){
        this.constants = new AlumnoConstants();
        this.alumnoRepository = alumnoRepository;
    }
    @Override
    public Mono<Void> crearAlumno(AlumnoRequest request) {
        Alumno alumno = new Alumno(null,request.getNombre(), request.getApellido(), request.getEstado(), request.getEdad());
        return alumnoRepository.save(alumno).then();
    }

    @Override
    public Flux<Alumno> obtenerAlumnosActivos() {
        return alumnoRepository.findByEstado(constants.ESTADO_ACTIVO);
    }
    
}
