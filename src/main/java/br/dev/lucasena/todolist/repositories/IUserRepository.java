package br.dev.lucasena.todolist.repositories;

import br.dev.lucasena.todolist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
