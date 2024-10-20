package br.dev.lucasena.todolist.controllers;

import br.dev.lucasena.todolist.core.cases.auth.GenerateTokenUseCase;
import br.dev.lucasena.todolist.core.cases.user.CreateUserUseCase;
import br.dev.lucasena.todolist.core.cases.user.FindUserByEmailUseCase;
import br.dev.lucasena.todolist.domain.Response;
import br.dev.lucasena.todolist.domain.auth.AuthDTO;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.domain.user.UserDTO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private FindUserByEmailUseCase findUserByEmailUseCase;
    @Autowired
    private GenerateTokenUseCase generateTokenUseCase;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Response<User>> create(@RequestBody @Valid UserDTO user) throws Exception {
        User newUser = createUserUseCase.execute(user);
        Response<User> response = new Response<>(newUser, "User created successfully.", false);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Response<String>> authenticate(@RequestBody @Valid AuthDTO auth) throws Exception {
        User user = findUserByEmailUseCase.execute(auth.getEmail());
        if (passwordEncoder.matches(auth.getPassword(), user.getPassword())) {
            String token = this.generateTokenUseCase.execute(user);
            Response<String> response = new Response<>(token, "User authenticated successfully", false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        Response<String> response = new Response<>(null, "Wrong credentials.", false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
