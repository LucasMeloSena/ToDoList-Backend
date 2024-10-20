package br.dev.lucasena.todolist.controllers;

import br.dev.lucasena.todolist.core.cases.task.CreateTaskUseCase;
import br.dev.lucasena.todolist.core.cases.task.FindTasksByUserUseCase;
import br.dev.lucasena.todolist.core.cases.task.UpdateTaskUseCase;
import br.dev.lucasena.todolist.domain.Response;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.domain.task.TaskDTO;

import br.dev.lucasena.todolist.domain.user.User;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    @Autowired
    private CreateTaskUseCase createTaskUseCase;
    @Autowired
    private FindTasksByUserUseCase findTasksByUserUseCase;
    @Autowired
    private UpdateTaskUseCase updateTaskUseCase;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Response<Task>> create(@RequestBody @Valid TaskDTO task) throws Exception{
        Task newTask = createTaskUseCase.execute(task);
        Response<Task> response = new Response<>(newTask, "Task created successfully", false);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<Response<List<Task>>> findByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Task> tasks = findTasksByUserUseCase.execute(user.getId());
        Response<List<Task>> response = new Response<>(tasks, "Tasks retrieved successfully", false);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Task>> update(@RequestBody TaskDTO taskToUpdate, @PathVariable String id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Task updatedTask = updateTaskUseCase.execute(taskToUpdate, id, user.getId());
        Response<Task> response = new Response<>(updatedTask, "Task updated successfully", false);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
