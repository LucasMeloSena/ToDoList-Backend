package br.dev.lucasena.todolist.controllers;

import br.dev.lucasena.todolist.core.cases.user.CreateUserUseCase;
import br.dev.lucasena.todolist.domain.Response;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.domain.user.UserDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Response<User>> create(@RequestBody @Valid UserDTO user) throws Exception {
        User newUser = createUserUseCase.execute(user);
        Response<User> response = new Response<>(newUser, "User created successfully.", false);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
