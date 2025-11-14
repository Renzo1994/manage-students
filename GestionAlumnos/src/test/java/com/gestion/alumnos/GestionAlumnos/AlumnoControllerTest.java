package com.gestion.alumnos.GestionAlumnos;

 

import com.gestion.alumnos.controller.AlumnoController;
import com.gestion.alumnos.domain.Alumno;
import com.gestion.alumnos.exception.AlumnoAlreadyExistsException;
import com.gestion.alumnos.exception.GlobalExceptionHandler;
import com.gestion.alumnos.service.AlumnoService;
import com.gestion.alumnos.web.dto.AlumnoRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(controllers = AlumnoController.class)
@Import(GlobalExceptionHandler.class)  // Importa tu manejador global de errores
class AlumnoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AlumnoService alumnoService;

    // POST /v1/alumnos/crear - éxito
    @Test
    void crearAlumno_debeRetornar200CuandoEsExitoso() {
        Alumno alumno = new Alumno("1", "Juan", "Perez", "ACTIVO", 20);

        Mockito.when(alumnoService.crearAlumno(any(AlumnoRequest.class)))
                .thenReturn(Mono.just(alumno));

        AlumnoRequest request = new AlumnoRequest(
                "1",
                "Juan",
                "Perez",
                "ACTIVO",
                20
        );

        webTestClient.post()
                .uri("/v1/alumnos/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty(); // el controller no devuelve body en éxito
    }

    // POST /v1/alumnos/crear - alumno ya existe → 409 + messages
    @Test
    void crearAlumno_debeRetornar409CuandoAlumnoYaExiste() {
        String errorMessage = "El alumno con este ID ya está registrado";

        Mockito.when(alumnoService.crearAlumno(any(AlumnoRequest.class)))
                .thenReturn(Mono.error(new AlumnoAlreadyExistsException(errorMessage)));

        AlumnoRequest request = new AlumnoRequest(
                "5",
                "Luis",
                "Gomez",
                "ACTIVO",
                25
        );

        webTestClient.post()
                .uri("/v1/alumnos/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("$.messages[0]").isEqualTo(errorMessage);
    }

    // POST /v1/alumnos/crear - error de validación → 400 + lista de mensajes
    @Test
    void crearAlumno_debeRetornar400CuandoRequestEsInvalido() {
        // Request inválido: nombre vacío y edad nula, por ejemplo
        AlumnoRequest request = new AlumnoRequest(
                "10",
                "",            // @NotBlank → error
                "Lopez",
                "ACTIVO",
                null           // @NotNull → error
        );

        webTestClient.post()
                .uri("/v1/alumnos/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.messages").isArray()
                .jsonPath("$.messages.length()").value(len -> {
                    // solo validamos que haya al menos 1 error
                    // si quieres ser más estricto puedes validar mensajes exactos
                    org.assertj.core.api.Assertions.assertThat((Integer) len).isGreaterThanOrEqualTo(1);
                });
    }

    // GET /v1/alumnos - lista de alumnos activos
    @Test
    void findAlumnosActives_debeRetornarListaDeAlumnosResponse() {
        Alumno alumno1 = new Alumno("1", "Juan", "Perez", "ACTIVO", 20);
        Alumno alumno2 = new Alumno("2", "Maria", "Lopez", "ACTIVO", 22);

        Mockito.when(alumnoService.obtenerAlumnosActivos())
                .thenReturn(Flux.just(alumno1, alumno2));

        webTestClient.get()
                .uri("/v1/alumnos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].nombre").isEqualTo("Juan")
                .jsonPath("$[0].apellido").isEqualTo("Perez")
                .jsonPath("$[0].estado").isEqualTo("ACTIVO")
                .jsonPath("$[0].edad").isEqualTo(20)

                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].nombre").isEqualTo("Maria")
                .jsonPath("$[1].apellido").isEqualTo("Lopez")
                .jsonPath("$[1].estado").isEqualTo("ACTIVO")
                .jsonPath("$[1].edad").isEqualTo(22);
    }
}
