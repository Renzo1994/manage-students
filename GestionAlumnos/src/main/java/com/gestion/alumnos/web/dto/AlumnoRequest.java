package com.gestion.alumnos.web.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlumnoRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String estado; // "ACTIVO" o "INACTIVO"

    @NotNull
    @Min(0)
    private Integer edad;

    public AlumnoRequest() {}

    public AlumnoRequest(String nombre, String apellido, String estado, Integer edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
        this.edad = edad;
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}
