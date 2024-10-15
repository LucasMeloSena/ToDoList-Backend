package br.dev.lucasena.todolist.core.exceptions.task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("The task you are trying to find don't exists.");
    }
}
