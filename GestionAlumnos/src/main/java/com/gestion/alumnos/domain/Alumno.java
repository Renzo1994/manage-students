package com.gestion.alumnos.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  // sirve para generar constructor sin parametros
@AllArgsConstructor  // sirve para generar constructor con parametros

@Table("alumno")
public class Alumno {

 
    @Id
    private String id;
    private String nombre;
    private String apellido;
    private String estado; // "ACTIVO" o "INACTIVO"
    private Integer edad;
 
 
}
