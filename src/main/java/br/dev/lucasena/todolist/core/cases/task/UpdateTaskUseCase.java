package br.dev.lucasena.todolist.core.cases.task;

import br.dev.lucasena.todolist.core.exceptions.task.TaskNotFoundException;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotAllowedException;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.domain.task.TaskDTO;
import br.dev.lucasena.todolist.domain.task.TaskMapper;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.repositories.ITaskRepository;
import br.dev.lucasena.todolist.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateTaskUseCase {
    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private TaskMapper mapper;

    @Transactional
    public Task execute(TaskDTO dto, String id, UUID userId) throws Exception {
        Task task = taskRepository.findById(UUID.fromString(id)).orElseThrow(TaskNotFoundException::new);

        if (!task.getUser().getId().equals(userId)) {
            throw new UserNotAllowedException();
        }

        if (dto.getUser_id() != null) {
            User user = userRepository.findById(dto.getUser_id()).orElseThrow(UserNotFoundException::new);
            task.setUser(user);
        }

        mapper.updateTaskFromDto(dto, task);
        return taskRepository.save(task);
    }
}
