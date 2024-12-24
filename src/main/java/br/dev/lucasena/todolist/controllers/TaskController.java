package br.dev.lucasena.todolist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.dev.lucasena.todolist.core.cases.task.CreateTaskUseCase;
import br.dev.lucasena.todolist.core.cases.task.DeleteTaskUseCase;
import br.dev.lucasena.todolist.core.cases.task.FindTasksByUserUseCase;
import br.dev.lucasena.todolist.core.cases.task.UpdateTaskUseCase;
import br.dev.lucasena.todolist.domain.Response;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.domain.task.TaskDTO;
import br.dev.lucasena.todolist.domain.user.User;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    @Autowired
    private CreateTaskUseCase createTaskUseCase;
    @Autowired
    private FindTasksByUserUseCase findTasksByUserUseCase;
    @Autowired
    private UpdateTaskUseCase updateTaskUseCase;
    @Autowired
    private DeleteTaskUseCase deleteTaskUseCase;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Response<Task>> create(@RequestBody @Valid TaskDTO task) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = authentication.getPrincipal();
        if (user instanceof User userInstance) {
            task.setUser_id(userInstance.getId());
            Task newTask = createTaskUseCase.execute(task);
            Response<Task> response = new Response<>(newTask, "Task created successfully", false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        Response<Task> response = new Response<>(null, "Error during getting user", true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<Response<List<Task>>> findByUser(@RequestParam(required = false) String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = authentication.getPrincipal();
        if (user instanceof User userInstance) {
            List<Task> tasks = findTasksByUserUseCase.execute(userInstance.getId(), name);
            Response<List<Task>> response = new Response<>(tasks, "Tasks retrieved successfully", false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        Response<List<Task>> response = new Response<>(null, "Error during getting user", true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Task>> update(@RequestBody TaskDTO taskToUpdate, @PathVariable String id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Task updatedTask = updateTaskUseCase.execute(taskToUpdate, id, user.getId());
        Response<Task> response = new Response<>(updatedTask, "Task updated successfully", false);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Task>> delete(@PathVariable String id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        deleteTaskUseCase.execute(id, user.getId());
        Response<Task> response = new Response<>(null, "Task deleted successfully", false);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
