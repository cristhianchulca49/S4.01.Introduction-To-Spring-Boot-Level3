package cat.itacademy.userapi.repository;

import cat.itacademy.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final List<User> dataBase = new ArrayList<>();

    @Override
    public void save(User user) {
        dataBase.add(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return dataBase.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public List<User> findByName(String name) {
        return dataBase.stream()
                .filter(user -> user.getName().toLowerCase().contains(name))
                .toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dataBase.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(dataBase);
    }

    @Override
    public void deleteById(UUID id) {
        dataBase.removeIf(user -> user.getId().equals(id));
    }
}
