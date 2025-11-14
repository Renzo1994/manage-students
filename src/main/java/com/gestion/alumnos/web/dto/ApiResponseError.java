package com.gestion.alumnos.web.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Respuesta de error estándar de la API")
public class ApiResponseError<T> {

    private List<String> messages;

    public ApiResponseError() {
    }

    public ApiResponseError(String message) {
        this.messages = List.of(message); // o Arrays.asList(message) si prefieres
    }

    public ApiResponseError(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
