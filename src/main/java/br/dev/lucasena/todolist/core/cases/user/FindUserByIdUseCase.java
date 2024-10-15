package br.dev.lucasena.todolist.core.cases.user;

import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class FindUserByIdUseCase {
    @Autowired
    private IUserRepository userRepository;

    public User execute(UUID id) throws Exception {
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
