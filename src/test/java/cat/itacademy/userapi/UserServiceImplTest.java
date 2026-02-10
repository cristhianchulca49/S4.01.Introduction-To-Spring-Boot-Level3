package cat.itacademy.userapi;

import cat.itacademy.userapi.dto.UserDto;
import cat.itacademy.userapi.exception.EmailAlreadyExistsException;
import cat.itacademy.userapi.model.User;
import cat.itacademy.userapi.repository.UserRepository;
import cat.itacademy.userapi.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        UserDto dto = new UserDto("pep", "pepito123@pepito.com");
        User existingUser = new User("pepito", "pepito123@pepito.com");

        Mockito.when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(existingUser));


        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(dto));
        Mockito.verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_shouldSaveAndReturnUser_WhenEmailIsUnique() {
        UserDto userDto = new UserDto("pepito", "pepito123@pepito.com");

        Mockito.when(userRepository.findByEmail(userDto.getEmail()))
                .thenReturn(Optional.empty());

        Mockito.when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User createdUser = userService.createUser(userDto);


        assertNotNull(createdUser.getId());
        assertFalse(createdUser.getId().toString().isBlank());

    }

    @Test
    void createUser_returnNewUser() {
        UserDto userDto = new UserDto("pepito", "pepito123@pepito.com");

        Mockito.when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(userDto.getName(), createdUser.getName());
        assertEquals(userDto.getEmail(), createdUser.getEmail());

        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }
}

