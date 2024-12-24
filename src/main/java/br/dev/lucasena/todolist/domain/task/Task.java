package br.dev.lucasena.todolist.domain.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.dev.lucasena.todolist.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

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
        this.status = taskDTO.getStatus();
        this.user = user;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;
    private String description;
    private LocalDateTime start_at;
    private LocalDateTime end_at;
      
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
