package br.dev.lucasena.todolist.domain.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthDTO{
    @NotBlank(message = "O email não pode ser nulo.")
    @Email(message = "O email fornecido não é válido.")
    private String email;

    @NotBlank(message = "A senha não pode ser nula.")
    @Size(min = 6, message = "A senha deve possuir no mínimo 6 caracteres.")
    private String password;
}
