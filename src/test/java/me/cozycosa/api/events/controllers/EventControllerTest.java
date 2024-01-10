package me.cozycosa.api.events.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.cozycosa.api.configuration.SpringSecurityConfiguration;
import me.cozycosa.api.events.DTO.EventDto;
import me.cozycosa.api.events.services.EventService;
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

@WebMvcTest(controllers = EventController.class)
@Import(SpringSecurityConfiguration.class)
public class EventControllerTest {
    private EventDto event1;
    private EventDto updatedEvent1;
    private UserDetails currentUser;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    WebApplicationContext context;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventService service;

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

        event1 = EventDto.builder()
                .id(1L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .title("Titre test")
                .content("Contenu test")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        updatedEvent1 = EventDto.builder()
                .id(1L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(1))
                .title("Titre test maj")
                .content("Contenu test maj")
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
    void testGetAllEvents() throws Exception {
        List<EventDto> eventList = new ArrayList<>();

        eventList.add(event1);

        when(service.findAll(ArgumentMatchers.any())).thenReturn(eventList);

        mockMvc.perform(get("/api/{homeId}/events", 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("[0].title", Matchers.equalTo("Titre test")));
    }

    @Test
    void testGetEventById() throws Exception {
        when(service.findById(ArgumentMatchers.any())).thenReturn(event1);

        mockMvc.perform(get("/api/{homeId}/events/{id}", 1, 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.title", Matchers.equalTo("Titre test")));
    }

    @Test
    void testCreateEvent() throws Exception {
        when(service.create(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(event1);

        String json = mapper.registerModule(new JavaTimeModule()).writeValueAsString(event1);

        mockMvc.perform(post("/api/{homeId}/events", 1)
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
    void testUpdateEvent() throws Exception {
        when(service.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(updatedEvent1);

        String json = mapper.registerModule(new JavaTimeModule()).writeValueAsString(event1);

        mockMvc.perform(put("/api/{homeId}/events/{id}", 1, 1)
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
    void testDeleteEvent() throws Exception {
        when(service.delete(ArgumentMatchers.any())).thenReturn("L'évènement a été supprimé");

        String response = mockMvc.perform(delete("/api/{homeId}/events/{id}", 1, 1)
                        .header("API-KEY", apiKey)
                        .with(user(userService.loadUserByUsername("admin@mail.com"))))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        assertEquals("", "L'évènement a été supprimé", response);
    }
}
