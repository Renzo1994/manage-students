package com.gestion.alumnos.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import com.gestion.alumnos.constants.AlumnoConstants;
import com.gestion.alumnos.domain.Alumno;
import com.gestion.alumnos.exception.AlumnoAlreadyExistsException;
import com.gestion.alumnos.repository.AlumnoRepository;
import com.gestion.alumnos.service.AlumnoService;
import com.gestion.alumnos.web.dto.AlumnoRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlumnoServiceImpl implements AlumnoService {
    private static final Logger logger = LoggerFactory.getLogger(AlumnoServiceImpl.class);
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    private final AlumnoRepository alumnoRepository;

    public AlumnoServiceImpl(
            AlumnoRepository alumnoRepository,
            R2dbcEntityTemplate r2dbcEntityTemplate

    ) {
        this.alumnoRepository = alumnoRepository;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;

    }

 

    @Override
    public Mono<Alumno> crearAlumno(AlumnoRequest request) {

        logger.info("Iniciando la creación del alumno con ID: {}", request.getId());
        return alumnoRepository.existsById(request.getId())
                .flatMap(exist -> {
                    if (exist) {
                        logger.warn("El alumno ya está registrado ID: {}", request.getId());
                        return Mono.error(new AlumnoAlreadyExistsException("El alumno con este ID ya está registrado"));
                    }
                    Alumno alumno = new Alumno(request.getId(), request.getNombre(), request.getApellido(),
                            request.getEstado(),
                            request.getEdad());
                    logger.info("Alumno creado exitosamente, ID : {}", alumno.getId());
                    return r2dbcEntityTemplate.insert(alumno);
                });
    }

    @Override
    public Flux<Alumno> obtenerAlumnosActivos() {
        logger.info("Obteniendo lista de alumnos activos...");
        return alumnoRepository.findByEstado(AlumnoConstants.ESTADO_ACTIVO);
    }

}
