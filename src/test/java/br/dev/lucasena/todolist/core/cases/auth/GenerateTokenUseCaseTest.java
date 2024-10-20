package br.dev.lucasena.todolist.core.cases.auth;

import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.domain.user.UserDTO;
import br.dev.lucasena.todolist.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class GenerateTokenUseCaseTest {
    @Mock
    private IUserRepository userRepository;

    @Autowired
    @InjectMocks
    private GenerateTokenUseCase generateTokenUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should generate token successfully")
    void execute1() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("johdoe@email.com");
        userDTO.setPassword("123456");
        User user = new User(userDTO);
        user.setId(UUID.randomUUID());

        when(userRepository.save(user)).thenReturn(user);

        String token = generateTokenUseCase.execute(user);

        assertNotNull(token);

        verify(userRepository, times(1)).save(user);
    }
}