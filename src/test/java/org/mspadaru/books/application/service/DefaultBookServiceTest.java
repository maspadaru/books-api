package org.mspadaru.books.application.service;

import org.junit.jupiter.api.Test;
import org.mspadaru.books.application.port.in.BookService;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultBookServiceTest {

    @Test
    void createBook_withValidBook_returnsBookWithId() {
        BookService bookService = new DefaultBookService();
        Book book = new Book(null, "Test Book", "11", LocalDate.now(),
                new HashSet<Author>());
        Book savedBook = bookService.createBook(book);
        assertNotNull(savedBook);
        assertNotNull(savedBook.id());
        assertEquals("Test Book", savedBook.title());
    }
}