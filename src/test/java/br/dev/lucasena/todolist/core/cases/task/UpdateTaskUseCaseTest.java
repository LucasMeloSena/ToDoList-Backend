package br.dev.lucasena.todolist.core.cases.task;

import br.dev.lucasena.todolist.core.exceptions.task.TaskNotFoundException;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.domain.task.TaskDTO;
import br.dev.lucasena.todolist.domain.task.TaskMapper;
import br.dev.lucasena.todolist.domain.task.TaskPriority;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.domain.user.UserDTO;
import br.dev.lucasena.todolist.repositories.ITaskRepository;
import br.dev.lucasena.todolist.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class UpdateTaskUseCaseTest {

    @Mock
    private ITaskRepository taskRepository;
    @Mock
    private TaskMapper mapper;
    @Mock
    private IUserRepository userRepository;

    @Autowired
    @InjectMocks
    private UpdateTaskUseCase updateTaskUseCase;

    @Autowired
    @InjectMocks
    private CreateTaskUseCase createTaskUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should update a task successfully")
    void execute1() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("johdoe@email.com");
        userDTO.setPassword("123456");
        User existingUser = new User(userDTO);
        existingUser.setId(UUID.randomUUID());

        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        LocalDateTime now = LocalDateTime.now();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("Test task");
        taskDTO.setDescription("Description");
        taskDTO.setStart_at(now.plusHours(1));
        taskDTO.setEnd_at(now.plusHours(2));
        taskDTO.setPriority(TaskPriority.medium);
        taskDTO.setUser_id(existingUser.getId());

        Task newTask = createTaskUseCase.execute(taskDTO);
        newTask.setId(UUID.randomUUID());
        when(taskRepository.findById(newTask.getId())).thenReturn(Optional.of(newTask));

        taskDTO.setName("Task updated");
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task taskToUpdate = invocation.getArgument(0);
            newTask.setName(taskToUpdate.getName());
            return newTask;
        });

        Task updatedTask = updateTaskUseCase.execute(taskDTO, newTask.getId().toString(), existingUser.getId());

        assertNotNull(updatedTask);
        assertNotEquals(updatedTask.getName(), taskDTO.getName());

        verify(userRepository, times(2)).findById(existingUser.getId());
        verify(mapper, times(1)).updateTaskFromDto(taskDTO, newTask);
        verify(taskRepository, times(2)).save(any(Task.class));
    }

    @Test
    @DisplayName("should throws exception if task not found")
    void execute2() {
        String nonExistentTaskId = UUID.randomUUID().toString();
        UUID userId = UUID.randomUUID();

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("Test task");

        when(taskRepository.findById(UUID.fromString(nonExistentTaskId))).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            updateTaskUseCase.execute(taskDTO, nonExistentTaskId, userId);
        });

        verify(taskRepository, times(1)).findById(UUID.fromString(nonExistentTaskId));
        verify(taskRepository, never()).save(any(Task.class));
    }
}