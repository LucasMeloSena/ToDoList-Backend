package br.dev.lucasena.todolist.core.cases.task;

import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.task.*;
import br.dev.lucasena.todolist.domain.user.*;
import br.dev.lucasena.todolist.repositories.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class CreateTaskUseCaseTest {
    @Mock
    private ITaskRepository taskRepository;
    @Mock
    private IUserRepository userRepository;

    @Autowired
    @InjectMocks
    private CreateTaskUseCase createTaskUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should create a task successfully")
    void execute1() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("johdoe@email.com");
        userDTO.setPassword("123456");
        User existingUser = new User(userDTO);

        LocalDateTime now = LocalDateTime.now();
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("Test task");
        taskDTO.setDescription("Description");
        taskDTO.setStart_at(now.plusHours(1));
        taskDTO.setEnd_at(now.plusHours(2));
        taskDTO.setPriority(TaskPriority.medium);
        taskDTO.setUser_id(existingUser.getId());

        when(userRepository.findById(taskDTO.getUser_id())).thenReturn(Optional.of(existingUser));

        Task newTask = createTaskUseCase.execute(taskDTO);

        assertNotNull(newTask);
        assertEquals(taskDTO.getName(), newTask.getName());

        verify(userRepository, times(1)).findById(existingUser.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("should throws exception if user don't exists to associate with task")
    void execute2() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("Test task");
        taskDTO.setDescription("Description");
        taskDTO.setStart_at(LocalDateTime.now());
        taskDTO.setEnd_at(LocalDateTime.now());
        taskDTO.setPriority(TaskPriority.medium);
        taskDTO.setUser_id(UUID.randomUUID());

        when(userRepository.findById(taskDTO.getUser_id())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            createTaskUseCase.execute(taskDTO);
        });

        verify(userRepository, times(1)).findById(taskDTO.getUser_id());
        verify(taskRepository, never()).save(any(Task.class));
    }
}