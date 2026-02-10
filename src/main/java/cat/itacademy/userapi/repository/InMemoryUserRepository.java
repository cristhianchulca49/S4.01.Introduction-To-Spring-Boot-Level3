package cat.itacademy.userapi.repository;

import cat.itacademy.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> dataBase = new HashMap<>();

    @Override
    public User save(User user) {
        dataBase.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(dataBase.get(id));
    }

    @Override
    public List<User> findByName(String name) {
        return dataBase.values().stream()
                .filter(user -> user.getName().toLowerCase().contains(name))
                .toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dataBase.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(dataBase.values());
    }

    @Override
    public void deleteById(UUID id) {
        dataBase.remove(id);
    }
}
