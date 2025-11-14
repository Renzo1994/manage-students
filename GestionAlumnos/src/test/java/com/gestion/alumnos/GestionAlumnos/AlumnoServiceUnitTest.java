package com.gestion.alumnos.GestionAlumnos;

 
import com.gestion.alumnos.domain.Alumno;
import com.gestion.alumnos.exception.AlumnoAlreadyExistsException;
import com.gestion.alumnos.repository.AlumnoRepository;
import com.gestion.alumnos.service.impl.AlumnoServiceImpl;
import com.gestion.alumnos.web.dto.AlumnoRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AlumnoServiceUnitTest {

    @Test
    void crearAlumno_alumnoYaExiste() {
        // Mock dependencias
        AlumnoRepository repository = Mockito.mock(AlumnoRepository.class);
        R2dbcEntityTemplate template = Mockito.mock(R2dbcEntityTemplate.class);

        Mockito.when(repository.existsById("5"))
                .thenReturn(Mono.just(true));

        // Corregido → 2 parámetros
        AlumnoServiceImpl service = new AlumnoServiceImpl(repository, template);

        AlumnoRequest request = new AlumnoRequest("5", "Luis", "Gomez", "ACTIVO", 30);

        StepVerifier.create(service.crearAlumno(request))
                .expectErrorMatches(ex ->
                        ex instanceof AlumnoAlreadyExistsException &&
                        ex.getMessage().equals("El alumno con este ID ya está registrado")
                )
                .verify();
    }


    @Test
    void crearAlumno_success() {
        // Mock dependencias
        AlumnoRepository repository = Mockito.mock(AlumnoRepository.class);
        R2dbcEntityTemplate template = Mockito.mock(R2dbcEntityTemplate.class);

        Mockito.when(repository.existsById("1"))
                .thenReturn(Mono.just(false));

        Mockito.when(template.insert(Mockito.any(Alumno.class)))
                .thenReturn(Mono.just(new Alumno("1", "Juan", "Perez", "ACTIVO", 20)));

        AlumnoServiceImpl service = new AlumnoServiceImpl(repository, template);

        AlumnoRequest request = new AlumnoRequest("1", "Juan", "Perez", "ACTIVO", 20);

        StepVerifier.create(service.crearAlumno(request))
                .expectNextMatches(al -> al.getId().equals("1"))
                .verifyComplete();
    }
}
