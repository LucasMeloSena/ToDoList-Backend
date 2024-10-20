package br.dev.lucasena.todolist.core.cases.task;

import br.dev.lucasena.todolist.core.exceptions.task.TaskNotFoundException;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.repositories.ITaskRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class FindTasksByUserUseCaseTest {
    @Mock
    private ITaskRepository taskRepository;

    @Autowired
    @InjectMocks
    private FindTasksByUserUseCase findTasksByUserUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should retrieve existing tasks by user")
    void execute1() throws Exception{
        UUID id = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(id);

        Task task1 = new Task();
        task1.setUser(existingUser);
        Task task2 = new Task();
        task2.setUser(existingUser);

        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findByUserId(id)).thenReturn(Optional.of(tasks));

        List<Task> foundTasks = findTasksByUserUseCase.execute(id);

        assertNotNull(foundTasks);
        assertEquals(2, (long) foundTasks.size());
        verify(taskRepository, times(1)).findByUserId(id);
    }

    @Test
    @DisplayName("should throws exception if don't exists tasks related with this user")
    void execute2() throws Exception {
        UUID id = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(id);

        Task task1 = new Task();
        task1.setUser(existingUser);
        Task task2 = new Task();
        task2.setUser(existingUser);

        when(taskRepository.findByUserId(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            findTasksByUserUseCase.execute(id);
        });

        verify(taskRepository, times(1)).findByUserId(id);
    }
}