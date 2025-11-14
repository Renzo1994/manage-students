package com.gestion.alumnos.web.dto;

 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // sirve para generar constructor sin parametros
@AllArgsConstructor // sirve para generar constructor con parametros
public class AlumnoResponse {

 
    private String id;

   
    private String nombre;

 
    private String apellido;

 
    private String estado;
 
    private Integer edad;

}
