package br.dev.lucasena.todolist.core.exceptions.user;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("The user you are trying to use don't exists.");
    }
}
