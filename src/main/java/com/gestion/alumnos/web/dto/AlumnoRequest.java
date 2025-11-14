package com.gestion.alumnos.web.dto;

import org.springframework.data.annotation.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // sirve para generar constructor sin parametros
@AllArgsConstructor // sirve para generar constructor con parametros
@Schema(description = "Request para crear un alumno")
public class AlumnoRequest {

 
    @Id
    @NotBlank(message = "El ID no puede estar vacío")
    @Size(max = 50, message = "El ID no puede tener más de 50 caracteres")
    private String id;
 
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 20, message = "El nombre no puede tener más de 20 caracteres")
    private String nombre;
 
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 20, message = "El apellido no puede tener más de 20 caracteres")
    private String apellido;
 
    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "ACTIVO|INACTIVO", flags = Pattern.Flag.CASE_INSENSITIVE, message = "El estado debe ser ACTIVO o INACTIVO")
     private String estado;  
 
    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 1, message = "La edad debe ser mayor   1")
    @Max(value = 100, message = "La edad no debe ser mayor a 100")
    private Integer edad;

}
