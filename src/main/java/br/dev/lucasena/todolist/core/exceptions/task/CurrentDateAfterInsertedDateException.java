package br.dev.lucasena.todolist.core.exceptions.task;

public class CurrentDateAfterInsertedDateException extends RuntimeException {
    public CurrentDateAfterInsertedDateException() {
        super("The inserted date is before the current date.");
    }
}
