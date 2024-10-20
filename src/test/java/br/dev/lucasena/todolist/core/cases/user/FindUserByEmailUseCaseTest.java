package br.dev.lucasena.todolist.core.cases.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindUserByEmailUseCaseTest {
    @Mock
    private IUserRepository userRepository;

    @Autowired
    @InjectMocks
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should retrieve an existing user by email")
    void execute1() throws Exception{
        String email = "johndoe@example.com";
        User existingUser = new User();
        existingUser.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        User foundUser = findUserByEmailUseCase.execute(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("should throws exception if don't exists a user with typed email")
    void execute2() throws Exception {
        String email = "johndoe@example.com";
        User existingUser = new User();
        existingUser.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            findUserByEmailUseCase.execute(email);
        });

        verify(userRepository, times(1)).findByEmail(email);
    }
}