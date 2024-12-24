package br.dev.lucasena.todolist.domain.task;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @NotNull(message = "O status não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private UUID user_id;
}
