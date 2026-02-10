package cat.itacademy.userapi.repository;

import cat.itacademy.userapi.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(UUID id);

    List<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void deleteById(UUID id);
}
