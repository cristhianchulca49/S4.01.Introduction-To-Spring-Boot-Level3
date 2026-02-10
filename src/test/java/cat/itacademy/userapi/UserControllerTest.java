package cat.itacademy.userapi;

import cat.itacademy.userapi.dto.UserDto;
import cat.itacademy.userapi.exception.UserNotFoundException;
import cat.itacademy.userapi.model.User;
import cat.itacademy.userapi.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserServiceImpl userServiceImpl;

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {
        Mockito.when(userServiceImpl.getUsers(Mockito.anyString()))
                .thenReturn(List.of());
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {
        User user = new User("Ada Lovelace", "ada@example.com");

        Mockito.when(userServiceImpl.createUser(any(UserDto.class)))
                .thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)
                        ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.name").value("Ada Lovelace"))
                .andExpect(jsonPath("$.email").value("ada@example.com"));
    }

    @Test
    void getUserById_returnsCorrectUser() throws Exception {
        User user = new User("Ada Lovelace", "ada@example.com");
        UUID userId = user.getId();

        Mockito.when(userServiceImpl.getUserById(userId))
                .thenReturn(user);

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))

                .andExpect(jsonPath("$.name").value("Ada Lovelace"))
                .andExpect(jsonPath("$.email").value("ada@example.com"));
    }

    @Test
    void getUserById_returnsNotFound404() throws Exception {
        UUID userId = UUID.randomUUID();
        String expectedMessage = String.format("User with id: %s not found", userId);

        Mockito.when(userServiceImpl.getUserById(userId))
                .thenThrow(new UserNotFoundException(userId));

        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void getUsers_withNameParam_returnFilteredUsers() throws Exception {
        List<User> users = List.of(
                new User("John Turing", "alan@example.com"),
                new User("Jonathan Cambridge", "jon@example.com")
        );

        String nameSearch = "Jo";

        Mockito.when(userServiceImpl.getUsers(nameSearch))
                .thenReturn(users);

        mockMvc.perform(get("/users").param("name", nameSearch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Turing"))
                .andExpect(jsonPath("$[1].name").value("Jonathan Cambridge"));
    }
}