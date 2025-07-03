package org.mspadaru.books.infrastructure.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mspadaru.books.application.port.in.BookService;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.infrastructure.web.dto.AuthorDto;
import org.mspadaru.books.infrastructure.web.dto.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book createSampleBook(String title) {
        return new Book(UUID.randomUUID(), title, "1234567890", LocalDate.now(),
                Set.of(new Author(UUID.randomUUID(), "Sample Author")));
    }

    @Test
    void getAllBooks_whenBooksArePresent_thenReturnsBooks() throws Exception {
        Book book1 = createSampleBook("First Book");
        Book book2 = createSampleBook("Second Book");
        when(bookService.findAllBooks()).thenReturn(Set.of(book1, book2));
        mockMvc.perform(get("/api/books")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getBookById_whenBookExists_thenReturnsBook() throws Exception {
        Book book = createSampleBook("Test Book");
        when(bookService.findBookById(book.id())).thenReturn(java.util.Optional.of(book));
        mockMvc.perform(get("/api/books/" + book.id())).andExpect(status().isOk()).andExpect(
                jsonPath("$.title", is(book.title())));
    }

    @Test
    void createBook_whenBookIsValid_thenReturnsBookWithId() throws Exception {
        BookRequest request = new BookRequest("Test Book", "1234567890", LocalDate.now(),
                Set.of(new AuthorDto(UUID.randomUUID(), "Sample Author")));
        Book savedBook = createSampleBook("Test Book");
        when(bookService.createBook(org.mockito.ArgumentMatchers.any(Book.class))).thenReturn(savedBook);
        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(request))).andExpect(status().isCreated()).andExpect(
                jsonPath("$.title", is(savedBook.title())));
    }

    @Test
    void updateBook_whenBookExists_thenReturnsUpdatedBook() throws Exception {
        BookRequest request = new BookRequest("Updated Book", "1234567890", LocalDate.now(),
                Set.of(new AuthorDto(UUID.randomUUID(), "Sample Author")));
        Book updatedBook = createSampleBook("Updated Book");
        UUID id = updatedBook.id();
        when(bookService.updateBook(org.mockito.ArgumentMatchers.eq(id),
                org.mockito.ArgumentMatchers.any(Book.class))).thenReturn(java.util.Optional.of(updatedBook));
        mockMvc.perform(put("/api/books/" + id).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andExpect(
                jsonPath("$.title", is(updatedBook.title())));
    }

    @Test
    void deleteBook_whenBookExists_thenReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        when(bookService.deleteBook(id)).thenReturn(true);
        mockMvc.perform(delete("/api/books/" + id)).andExpect(status().isNoContent());
    }

    @Test
    void deleteBook_whenBookDoesNotExist_thenReturnsNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(bookService.deleteBook(id)).thenReturn(false);
        mockMvc.perform(delete("/api/books/" + id)).andExpect(status().isNotFound());
    }

}