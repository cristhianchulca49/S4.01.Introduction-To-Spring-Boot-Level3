package cat.itacademy.userapi.service;

import cat.itacademy.userapi.dto.UserDto;
import cat.itacademy.userapi.exception.UserAlreadyExistsException;
import cat.itacademy.userapi.exception.UserNotFoundException;
import cat.itacademy.userapi.model.User;
import cat.itacademy.userapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(userDto.getEmail());
                });

        User newUser = new User(userDto.getName(), userDto.getEmail());
        return userRepository.save(newUser);
    }


    @Override
    public List<User> getUsers(String name) {
        if (name == null || name.isBlank()) {
            return userRepository.findAll();
        }

        String nameSearch = name.toLowerCase();
        return userRepository.findByName(nameSearch);
    }


    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
