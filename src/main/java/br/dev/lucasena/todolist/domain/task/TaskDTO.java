package br.dev.lucasena.todolist.domain.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskDTO {
    @NotBlank(message = "O nome não pode ser nulo.")
    private String name;

    @NotBlank(message = "A descrição não pode ser nula.")
    private String description;

    @NotNull(message = "A data de início não pode ser nula.")
    private LocalDateTime start_at;

    @NotNull(message = "A data de término não pode ser nula.")
    private LocalDateTime end_at;

    @NotNull(message = "A prioridade não pode ser nula.")
    private TaskPriority priority;

    @NotNull(message = "O id do usuário não pode ser nulo.")
    private UUID user_id;
}
