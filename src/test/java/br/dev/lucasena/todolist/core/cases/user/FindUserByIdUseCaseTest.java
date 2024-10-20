package br.dev.lucasena.todolist.core.cases.user;

import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class FindUserByIdUseCaseTest {

    @Mock
    private IUserRepository userRepository;

    @Autowired
    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should retrieve an existing user by id")
    void execute1() throws Exception{
        UUID id = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        User foundUser = findUserByIdUseCase.execute(id);

        assertNotNull(foundUser);
        assertEquals(id, foundUser.getId());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should throws exception if don't exists a user with typed id")
    void execute2() throws Exception {
        UUID id = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            findUserByIdUseCase.execute(id);
        });

        verify(userRepository, times(1)).findById(id);
    }
}