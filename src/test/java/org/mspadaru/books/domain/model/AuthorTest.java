package org.mspadaru.books.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthorTest {
    @Test
    void newAuthor_whenValidName_thenCreatesValidAuthor() {
        var id = UUID.randomUUID();
        var author = new Author(id, "George Orwell");
        assertEquals("George Orwell", author.name());
    }

    @Test
    void newAuthor_whenNameIsNull_thenThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Author(UUID.randomUUID(), null));
    }

    @Test
    void newAuthor_whenNameIsBlank_thenThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Author(UUID.randomUUID(), "  "));
    }

}