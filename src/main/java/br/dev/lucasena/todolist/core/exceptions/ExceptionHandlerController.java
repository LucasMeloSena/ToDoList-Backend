package br.dev.lucasena.todolist.core.exceptions;

import br.dev.lucasena.todolist.core.exceptions.task.CurrentDateAfterInsertedDateException;
import br.dev.lucasena.todolist.core.exceptions.task.StartDateIsAfterEndDateException;
import br.dev.lucasena.todolist.core.exceptions.task.TaskNotFoundException;
import br.dev.lucasena.todolist.core.exceptions.user.UserAlreadyExistsException;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotAllowedException;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> parsingDtoError(MethodArgumentNotValidException e) {
        Map<String, String> mappingErrors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((err) -> {
            String field = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            mappingErrors.put(field, message);
        });

        Response<Map<String, String>> response = new Response<>(mappingErrors, "Error parsing request body.", true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Response<Void>> userAlreadyExists(UserAlreadyExistsException e) {
        Response<Void> response = new Response<>(null, e.getMessage(), true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response<Void>> userNotFound(UserNotFoundException e) {
        Response<Void> response = new Response<>(null, e.getMessage(), true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CurrentDateAfterInsertedDateException.class)
    public ResponseEntity<Response<Void>> wrongInsertedDate(CurrentDateAfterInsertedDateException e) {
        Response<Void> response = new Response<>(null, e.getMessage(), true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(StartDateIsAfterEndDateException.class)
    public ResponseEntity<Response<Void>> wrongInsertedStartDate(StartDateIsAfterEndDateException e) {
        Response<Void> response = new Response<>(null, e.getMessage(), true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Response<Void>> taskNotFound(TaskNotFoundException e) {
        Response<Void> response = new Response<>(null, e.getMessage(), true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotAllowedException.class)
    public ResponseEntity<Response<Void>> taskNotFound(UserNotAllowedException e) {
        Response<Void> response = new Response<>(null, e.getMessage(), true);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
