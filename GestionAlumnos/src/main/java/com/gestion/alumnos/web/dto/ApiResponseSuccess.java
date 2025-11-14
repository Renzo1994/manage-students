package com.gestion.alumnos.web.dto;

 import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
 
public class ApiResponseSuccess<T> {

    private String message;

        // Constructor para respuesta de éxito
    public ApiResponseSuccess(String message) {
       this.message = message;

    }

    // Constructor para respuesta de error
    public ApiResponseSuccess() {
   
    }
    
}
