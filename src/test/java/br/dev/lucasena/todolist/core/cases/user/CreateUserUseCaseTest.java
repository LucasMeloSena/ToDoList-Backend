package br.dev.lucasena.todolist.core.cases.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.dev.lucasena.todolist.core.exceptions.user.UserAlreadyExistsException;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.domain.user.UserDTO;
import br.dev.lucasena.todolist.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUserUseCaseTest {
    @Mock
    private IUserRepository userRepository;

    @Autowired
    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should create an user successfully")
    void execute1() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("johdoe@email.com");
        userDTO.setPassword("123456");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        User newUser = createUserUseCase.execute(userDTO);

        assertNotNull(newUser);
        assertEquals(userDTO.getEmail(), newUser.getEmail());
        assertNotEquals(userDTO.getPassword(), newUser.getPassword());

        BCrypt.Result isPassEncrypted = BCrypt.verifyer().verify(userDTO.getPassword().toCharArray(), newUser.getPassword());
        assertTrue(isPassEncrypted.verified);

        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("should throw exception if user already exists")
    void execute2() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("johdoe@email.com");
        userDTO.setPassword("123456");

        User existingUser = new User(userDTO);
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> {
            createUserUseCase.execute(userDTO);
        });

        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
}