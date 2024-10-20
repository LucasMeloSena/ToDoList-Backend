package br.dev.lucasena.todolist.core.cases.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.dev.lucasena.todolist.core.exceptions.user.UserAlreadyExistsException;
import br.dev.lucasena.todolist.domain.user.User;
import br.dev.lucasena.todolist.domain.user.UserDTO;
import br.dev.lucasena.todolist.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserUseCase {
    @Autowired
    private IUserRepository userRepository;

    public User execute(UserDTO user) throws Exception {
        Optional<User> userAlreadyExists = this.userRepository.findByEmail(user.getEmail());
        if (userAlreadyExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User newUser = new User(user);
        String cryptPass = BCrypt.withDefaults()
                .hashToString(12, newUser.getPassword().toCharArray());
        newUser.setPassword(cryptPass);

        this.userRepository.save(newUser);
        return newUser;
    }
}
