package org.mspadaru.books.infrastructure.persistence.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class BookJpaAdapterIntegrationTest {

    @Autowired
    BookJpaAdapter adapter;

    private Book createBookWithSuffix(UUID id, String suffix) {
        String title = "Test " + suffix;
        String isbn = "123-" + suffix;
        LocalDate date = LocalDate.now();
        Set<Author> authors = Set.of();
        Book book = new Book(id, title, isbn, date, authors);
        return adapter.create(book);
    }

    @Test
    void create_whenBookIsValid_theReturnsBookWithId() {
        Book savedBook = createBookWithSuffix(null, "1");
        assertNotNull(savedBook.id());
        assertEquals("Test 1", savedBook.title());
    }


    @Test
    void findAll_whenBooksExist_thenReturnsAllBooks() {
        int expectedSize = 3;
        for (int i = 0; i < expectedSize; i++) {
            createBookWithSuffix(null, Integer.toString(i));
        }
        Set<Book> result = adapter.findAll();
        assertEquals(expectedSize, result.size());
        assertTrue(result.stream().anyMatch(book -> book.title().equals("Test 1")));
    }

    @Test
    void findAll_whenNoBooksExist_thenReturnsEmptySet() {
        Set<Book> result = adapter.findAll();
        assertTrue(result.isEmpty());
    }

}