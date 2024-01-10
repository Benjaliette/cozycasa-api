package me.cozycosa.api.homes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.cozycosa.api.configuration.SpringSecurityConfiguration;
import me.cozycosa.api.homes.DTO.HomeDto;
import me.cozycosa.api.homes.services.HomeService;
import me.cozycosa.api.users.DTO.UserDto;
import me.cozycosa.api.users.entities.UserEntity;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HomeController.class)
@Import(SpringSecurityConfiguration.class)
public class HomeControllerTest {
    private HomeDto home1;
    private HomeDto home2;
    private UserEntity currentUserEntity;
    private UserDto currentUserDto;
    private UserDto otherUserDto;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    WebApplicationContext context;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    HomeService homeService;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();

        currentUserEntity = UserEntity.builder()
                .username("admin@mail.com")
                .password("123456")
                .build();

        currentUserDto = UserDto.builder()
                .username("admin@mail.com")
                .password("123456")
                .build();

        otherUserDto = UserDto.builder()
                .username("user@mail.com")
                .password("azerty")
                .build();

        home1 = HomeDto.builder()
                .id(1L)
                .name("Maison test")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        home2 = HomeDto.builder()
                .id(1L)
                .name("Maison test updated")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userService.loadUserByUsername(ArgumentMatchers.any())).thenReturn(currentUserEntity);
    }

    @Test
    void testGetAllHomes() throws Exception {
        List<HomeDto> homeList = new ArrayList<>();

        homeList.add(home1);

        when(homeService.findAll()).thenReturn(homeList);

        mockMvc.perform(get("/api/homes")
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("[0].name", Matchers.equalTo("Maison test")));
    }

    @Test
    void testGetHomeById() throws Exception {
        when(homeService.findById(ArgumentMatchers.any())).thenReturn(home1);

        mockMvc.perform(get("/api/homes/{id}", 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Maison test")));
    }

    @Test
    void testCreateHome() throws Exception {
        when(homeService.create(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(home1);

        String json = mapper.registerModule(new JavaTimeModule()).writeValueAsString(home1);

        mockMvc.perform(post("/api/homes")
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Maison test")));
    }

    @Test
    void testUpdateHome() throws Exception {
        when(homeService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(home2);

        String json = mapper.registerModule(new JavaTimeModule()).writeValueAsString(home2);

        mockMvc.perform(put("/api/homes/{id}", 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Maison test updated")));
    }

    @Test
    void testDeleteHome() throws Exception {
        when(homeService.delete(ArgumentMatchers.any())).thenReturn("Le foyer a été supprimé");

        String response = mockMvc.perform(delete("/api/homes/{id}", 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        assertEquals("", "Le foyer a été supprimé", response);
    }
}
