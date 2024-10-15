package br.dev.lucasena.todolist.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Response<T> {
    private T data;
    private String message;
    private Boolean error;
    private Date timestamp;

    public Response (T data, String message, Boolean error) {
        this.data = data;
        this.message = message;
        this.error = error;
        this.timestamp = new Date();
    }
}
