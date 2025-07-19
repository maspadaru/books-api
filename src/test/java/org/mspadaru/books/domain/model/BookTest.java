package org.mspadaru.books.domain.model;

import org.junit.jupiter.api.Test;
import org.mspadaru.books.domain.model.constraints.BookConstraints;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    private final Author validAuthor = new Author(UUID.randomUUID(), "George Orwell");

    @Test
    void newBook_whenAllFieldsValid_thenCreatesValidBook() {
        var book = new Book(UUID.randomUUID(), "1984", "1234567890123", LocalDate.of(1949, 6, 8), Set.of(validAuthor));
        assertEquals("1984", book.title());
    }

    @Test
    void newBook_whenTitleIsNull_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), null, "isbn", LocalDate.now(), Set.of(validAuthor)));
    }

    @Test
    void newBook_whenTitleIsBlank_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), "  ", "isbn", LocalDate.now(), Set.of(validAuthor)));
    }

    @Test
    void newBook_whenTitleExceedsMaxLength_thenThrows() {
        String longTitle = "A".repeat(BookConstraints.TITLE_MAX_LENGTH + 1);
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), longTitle, "isbn", LocalDate.now(), Set.of(validAuthor)));
    }

    @Test
    void newBook_whenIsbnIsNull_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), "Book Title", null, LocalDate.now(), Set.of(validAuthor)));
    }

    @Test
    void newBook_whenIsbnIsBlank_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), "Book Title", "  ", LocalDate.now(), Set.of(validAuthor)));
    }

    @Test
    void newBook_whenIsbnExceedsMaxLength_thenThrows() {
        String longIsbn = "1".repeat(BookConstraints.ISBN_MAX_LENGTH + 1); //
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), "Book Title", longIsbn, LocalDate.now(), Set.of(validAuthor)));
    }

    @Test
    void newBook_whenPublishedDateIsNull_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), "Book Title", "isbn", null, Set.of(validAuthor)));
    }

    @Test
    void newBook_whenAuthorsIsNull_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), "Book Title", "isbn", LocalDate.now(), null));
    }

    @Test
    void newBook_whenAuthorsIsEmpty_thenThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(UUID.randomUUID(), "Book Title", "isbn", LocalDate.now(), Set.of()));
    }
}
