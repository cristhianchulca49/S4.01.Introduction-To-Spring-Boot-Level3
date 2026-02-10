package cat.itacademy.userapi.service;

import cat.itacademy.userapi.dto.UserDto;
import cat.itacademy.userapi.exception.UserNotFoundException;
import cat.itacademy.userapi.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final List<User> dataBase = new ArrayList<>();

    public List<User> getUsers(String name) {
        if (name == null || name.isBlank()) {
            return List.copyOf(dataBase);
        }
        String query = name.toLowerCase();
        return dataBase.stream()
                .filter(u -> u.getName().toLowerCase().contains(query))
                .toList();
    }

    public User createUser(UserDto userDto) {
        User user = new User(userDto.getName(), userDto.getEmail());
        dataBase.add(user);
        return user;
    }

    public User getUserById(UUID id) {
        return dataBase.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
