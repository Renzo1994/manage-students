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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/v1/alumnos")
@RestController
@Tag(name = "Alumnos", description = "Operaciones para gestionar alumnos")
public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @Operation(summary = "Crear un nuevo alumno", description = "Crea un alumno si los datos son válidos y el ID no existe.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alumno creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación del request", content = @Content(mediaType = "application/json", schema = @Schema(example = "{ \"messages\": [\"El nombre no puede estar vacío\"] }"))),
            @ApiResponse(responseCode = "409", description = "El alumno ya existe", content = @Content(mediaType = "application/json", schema = @Schema(example = "{ \"messages\": [\"El alumno con este ID ya está registrado\"] }")))
    })
    @PostMapping("/crear")
    public Mono<ResponseEntity<Object>> crear(@Valid @RequestBody AlumnoRequest request) {
        return alumnoService.crearAlumno(request)
                .then(Mono.just(ResponseEntity.ok().<Object>build()));
    }

    @Operation(summary = "Listar alumnos activos", description = "Retorna todos los alumnos cuyo estado es ACTIVO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de alumnos activos", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AlumnoResponse.class))))
    })
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