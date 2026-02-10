package cat.itacademy.userapi.service;

import cat.itacademy.userapi.dto.UserDto;
import cat.itacademy.userapi.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getUsers(String name);

    User createUser(UserDto userDto);

    User getUserById(UUID id);
}
