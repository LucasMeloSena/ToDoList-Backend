package br.dev.lucasena.todolist.core.cases.user;

import br.dev.lucasena.todolist.core.exceptions.user.UserNotFoundException;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindUserByEmailUseCase {
    @Autowired
    private IUserRepository userRepository;

    public User execute(String email) throws Exception {
        return this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
