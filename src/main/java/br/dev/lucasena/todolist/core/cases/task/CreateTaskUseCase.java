package br.dev.lucasena.todolist.core.cases.task;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.lucasena.todolist.core.exceptions.task.CurrentDateAfterInsertedDateException;
import br.dev.lucasena.todolist.core.exceptions.task.StartDateIsAfterEndDateException;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.domain.task.TaskDTO;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.repositories.ITaskRepository;
import br.dev.lucasena.todolist.repositories.IUserRepository;

@Service
public class CreateTaskUseCase {
    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private IUserRepository userRepository;

    public Task execute(TaskDTO task) throws Exception {
        User existsUser = this.userRepository.findById(task.getUser_id()).orElseThrow(UserNotFoundException::new);

        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(task.getStart_at().toLocalDate()) || currentDate.isAfter(task.getEnd_at().toLocalDate())) {
            throw new CurrentDateAfterInsertedDateException();
        }
        if (task.getStart_at().toLocalDate().isAfter(task.getEnd_at().toLocalDate())) {
            throw new StartDateIsAfterEndDateException();
        }

        Task newTask = new Task(task, existsUser);
        this.taskRepository.save(newTask);
        return newTask;
    }
}
