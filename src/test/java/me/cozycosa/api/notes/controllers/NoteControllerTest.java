package me.cozycosa.api.notes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.cozycosa.api.configuration.SpringSecurityConfiguration;
import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.entities.NoteEntity;
import me.cozycosa.api.notes.services.NoteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NoteController.class)
@Import(SpringSecurityConfiguration.class)
public class NoteControllerTest {
    private NoteDto note1;
    private NoteDto updatedNote1;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NoteService service;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();

        note1 = NoteDto.builder()
                .id(1L)
                .title("Titre test")
                .content("Contenu test")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        updatedNote1 = NoteDto.builder()
                .id(1L)
                .title("Titre test màj")
                .content("Mise à jour du contenu test")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetAllNotes() throws Exception {
        List<NoteDto> noteList = new ArrayList<>();

        noteList.add(note1);

        when(service.findAll()).thenReturn(noteList);

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("[0].title", Matchers.equalTo("Titre test")));
    }

    @Test
    void testGetNotesById() throws Exception {
        when(service.findById(ArgumentMatchers.any())).thenReturn(note1);

        mockMvc.perform(get("/api/notes/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.title", Matchers.equalTo("Titre test")));
    }

    @Test
    void testCreateNote() throws Exception {
        when(service.create(ArgumentMatchers.any())).thenReturn(note1);

        String json = mapper.registerModule(new JavaTimeModule()).writeValueAsString(note1);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.title", Matchers.equalTo("Titre test")));
    }

    @Test
    void testUpdateNote() throws Exception {
        when(service.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(updatedNote1);

        String json = mapper.registerModule(new JavaTimeModule()).writeValueAsString(note1);

        mockMvc.perform(put("/api/notes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.title", Matchers.equalTo("Titre test màj")));
    }

    @Test
    void testDeleteNote() throws Exception {
        when(service.delete(ArgumentMatchers.any())).thenReturn("La note a été supprimée");

        String response = mockMvc.perform(delete("/api/notes/{id}", 1))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        assertEquals("", response, "La note a été supprimée");
    }
}