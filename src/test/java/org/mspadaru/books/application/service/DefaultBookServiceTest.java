package org.mspadaru.books.application.service;

import org.junit.jupiter.api.Test;
import org.mspadaru.books.application.port.in.BookService;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.domain.port.out.BookRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultBookServiceTest {

    @Test
    void createBook_whenBookIsValid_thenReturnsBookWithId() {
        BookRepository mockRepository = mock(BookRepository.class);
        DefaultBookService service = new DefaultBookService(mockRepository);
        Book inputBook = new Book(null, "Test Book", "1234567890",
                LocalDate.now(), Set.of());
        Book savedBook = new Book(UUID.randomUUID(), "Test Book", "1234567890",
                LocalDate.now(), Set.of());
        when(mockRepository.create(inputBook)).thenReturn(savedBook);
        Book result = service.createBook(inputBook);
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("Test Book", result.title());
    }

}