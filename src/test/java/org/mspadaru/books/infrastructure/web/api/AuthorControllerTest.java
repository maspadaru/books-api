package org.mspadaru.books.infrastructure.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mspadaru.books.application.port.in.AuthorService;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.infrastructure.web.dto.AuthorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAuthors_whenAuthorsArePresent_thenReturnsAuthors() throws Exception {
        Author author1 = new Author(UUID.randomUUID(), "First Author");
        Author author2 = new Author(UUID.randomUUID(), "Second Author");

        when(authorService.findAllAuthors()).thenReturn(Set.of(author1, author2));

        mockMvc.perform(get("/api/authors")).andExpect(status().isOk()).andExpect(
                jsonPath("$.length()", Matchers.is(2))).andExpect(
                jsonPath("$[*].name", Matchers.containsInAnyOrder("First Author", "Second Author")));
    }

    @Test
    void createAuthor_whenAuthorIsValid_theReturnsAuthorWithId() throws Exception {
        AuthorRequest request = new AuthorRequest("New Author");
        Author savedAuthor = new Author(UUID.randomUUID(), "New Author");

        when(authorService.createAuthor(any())).thenReturn(savedAuthor);

        mockMvc.perform(post("/api/authors").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(request))).andExpect(status().isCreated()).andExpect(
                jsonPath("$.id").value(savedAuthor.id().toString())).andExpect(jsonPath("$.name").value("New Author"));
    }

    @Test
    void createAuthor_whenNameIsBlank_thenReturnsBadRequest() throws Exception {
        AuthorRequest invalidRequest = new AuthorRequest("   "); // just spaces
        String json = objectMapper.writeValueAsString(invalidRequest);

        mockMvc.perform(post("/api/authors").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(
                status().isBadRequest());
    }

    @Test
    void deleteAuthor_whenAuthorIsPresent_thenReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        when(authorService.deleteAuthor(id)).thenReturn(true);

        mockMvc.perform(delete("/api/authors/" + id)).andExpect(status().isNoContent());
    }

    @Test
    void deleteAuthor_whenAuthorIsNotPresent_thenReturnsNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(authorService.deleteAuthor(id)).thenReturn(false);

        mockMvc.perform(delete("/api/authors/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void deleteAuthor_whenUuidIsMalformed_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/authors/not-a-uuid")).andExpect(status().isBadRequest());
    }

    @Test
    void getAuthorById_whenAuthorExists_thenReturnsAuthor() throws Exception {
        UUID id = UUID.randomUUID();
        Author author = new Author(id, "Existing Author");

        when(authorService.findAuthorById(id)).thenReturn(java.util.Optional.of(author));

        mockMvc.perform(get("/api/authors/" + id)).andExpect(status().isOk()).andExpect(
                jsonPath("$.id").value(id.toString())).andExpect(jsonPath("$.name").value("Existing Author"));
    }

    @Test
    void getAuthorById_whenAuthorNotFound_thenReturnsNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(authorService.findAuthorById(id)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/authors/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void getAuthorById_whenUuidIsMalformed_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/authors/not-a-uuid")).andExpect(status().isBadRequest());
    }

    @Test
    void updateAuthor_whenAuthorExists_thenReturnsUpdatedAuthor() throws Exception {
        UUID id = UUID.randomUUID();
        AuthorRequest request = new AuthorRequest("Updated Name");
        Author updated = new Author(id, "Updated Name");

        when(authorService.updateAuthor(id, new Author(id, "Updated Name"))).thenReturn(java.util.Optional.of(updated));

        mockMvc.perform(put("/api/authors/" + id).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andExpect(
                jsonPath("$.id").value(id.toString())).andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void updateAuthor_whenAuthorNotFound_thenReturnsNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        AuthorRequest request = new AuthorRequest("New Name");

        when(authorService.updateAuthor(id, new Author(id, "New Name"))).thenReturn(java.util.Optional.empty());

        mockMvc.perform(put("/api/authors/" + id).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(request))).andExpect(status().isNotFound());
    }

    @Test
    void updateAuthor_whenNameIsBlank_thenReturnsBadRequest() throws Exception {
        AuthorRequest invalidRequest = new AuthorRequest("  ");
        UUID id = UUID.randomUUID();

        mockMvc.perform(put("/api/authors/" + id).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(invalidRequest))).andExpect(status().isBadRequest());
    }

}
