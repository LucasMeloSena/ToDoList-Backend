package br.dev.lucasena.todolist.core.cases.task;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.lucasena.todolist.core.exceptions.task.TaskNotFoundException;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotAllowedException;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.repositories.ITaskRepository;
import br.dev.lucasena.todolist.repositories.IUserRepository;

@Service
public class DeleteTaskUseCase {
  @Autowired
  private ITaskRepository taskRepository;
  @Autowired
  private IUserRepository userRepository;

  public void execute(String id, UUID userId) throws Exception {
    userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    Task task = taskRepository.findById(UUID.fromString(id)).orElseThrow(TaskNotFoundException::new);

    if (!task.getUser().getId().equals(userId)) {
      throw new UserNotAllowedException();
    }

    taskRepository.delete(task);
  }
}

