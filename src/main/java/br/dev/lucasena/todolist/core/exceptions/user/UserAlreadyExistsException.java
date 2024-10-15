package br.dev.lucasena.todolist.core.exceptions.user;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("User already exists.");
    }
}
