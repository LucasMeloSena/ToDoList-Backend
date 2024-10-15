package br.dev.lucasena.todolist.core.exceptions.user;

public class UserNotAllowedException extends RuntimeException {
    public UserNotAllowedException() {
        super("This user isn't allowed to do this operation.");
    }
}
