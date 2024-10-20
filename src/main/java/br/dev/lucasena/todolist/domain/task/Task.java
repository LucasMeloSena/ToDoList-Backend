package br.dev.lucasena.todolist.domain.task;

import br.dev.lucasena.todolist.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tasks")
public class Task {
    public Task() {}

    public Task (TaskDTO taskDTO, User user) {
        this.name = taskDTO.getName();
        this.description = taskDTO.getDescription();
        this.start_at = taskDTO.getStart_at();
        this.end_at = taskDTO.getEnd_at();
        this.priority = taskDTO.getPriority();
        this.user = user;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;
    private String description;
    private LocalDateTime start_at;
    private LocalDateTime end_at;
    private TaskPriority priority;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
