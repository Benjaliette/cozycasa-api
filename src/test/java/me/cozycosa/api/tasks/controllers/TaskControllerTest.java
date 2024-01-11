package me.cozycosa.api.tasks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.cozycosa.api.configuration.SpringSecurityConfiguration;
import me.cozycosa.api.tasks.DTO.TaskDto;
import me.cozycosa.api.tasks.services.TaskService;
import me.cozycosa.api.users.services.TokenBlackList;
import me.cozycosa.api.users.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
@Import(SpringSecurityConfiguration.class)
public class TaskControllerTest {
    private TaskDto task1;
    private TaskDto updatedTask1;
    private UserDetails currentUser;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    WebApplicationContext context;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService service;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationManager authManager;

    @MockBean
    TokenBlackList tokenBlackList;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();

        task1 = TaskDto.builder()
                .id(1L)
                .title("Titre test")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        updatedTask1 = TaskDto.builder()
                .id(1L)
                .title("Titre test maj")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        currentUser = User.builder()
                .username("admin@mail.com")
                .password("123456")
                .build();

        when(userService.loadUserByUsername(ArgumentMatchers.any())).thenReturn(currentUser);
    }

    @Test
    void testGetAllTasks() throws Exception {
        List<TaskDto> taskList = new ArrayList<>();

        taskList.add(task1);

        when(service.findAll(ArgumentMatchers.any())).thenReturn(taskList);

        mockMvc.perform(get("/api/{homeId}/tasks", 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("[0].title", Matchers.equalTo("Titre test")));
    }

    @Test
    void testGetTaskById() throws Exception {
        when(service.findById(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(task1);

        mockMvc.perform(get("/api/{homeId}/tasks/{id}", 1, 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.title", Matchers.equalTo("Titre test")));
    }

    @Test
    void testCreateTask() throws Exception {
        when(service.create(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(task1);

        String json = mapper.registerModule(new JavaTimeModule()).writeValueAsString(task1);

        mockMvc.perform(post("/api/{homeId}/tasks", 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.title", Matchers.equalTo("Titre test")));
    }

    @Test
    void testUpdateTask() throws Exception {
        when(service.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(updatedTask1);

        String json = mapper.registerModule(new JavaTimeModule()).writeValueAsString(task1);

        mockMvc.perform(put("/api/{homeId}/tasks/{id}", 1, 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.title", Matchers.equalTo("Titre test maj")));
    }

    @Test
    void testDeleteTask() throws Exception {
        when(service.delete(ArgumentMatchers.any())).thenReturn("La tâche a été supprimé");

        String response = mockMvc.perform(delete("/api/{homeId}/tasks/{id}", 1, 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        assertEquals("", "La tâche a été supprimé", response);
    }
}
