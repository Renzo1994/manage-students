package com.gestion.alumnos.web.dto;

import java.util.List;

import lombok.Data;

@Data

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
