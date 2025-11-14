package com.gestion.alumnos.exception;

public class AlumnoAlreadyExistsException extends RuntimeException{
    public AlumnoAlreadyExistsException (String message){
        super(message);
    }
    
}
