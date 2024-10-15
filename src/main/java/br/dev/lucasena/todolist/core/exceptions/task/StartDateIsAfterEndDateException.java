package br.dev.lucasena.todolist.core.exceptions.task;

public class StartDateIsAfterEndDateException extends RuntimeException {
    public StartDateIsAfterEndDateException() {
        super("The inserted start date is after the end date.");
    }
}
