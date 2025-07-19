package org.mspadaru.books.infrastructure.persistence.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.domain.port.out.AuthorRepository;
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

    @Autowired
    AuthorRepository authorJpaAdapter;

    private Book createBook(String title, String isbn, LocalDate date, Set<Author> authors) {
        Book book = new Book(null, title, isbn, date, authors);
        return adapter.create(book);
    }

    private Author createAuthor(String name) {
        return authorJpaAdapter.create(new Author(null, name));
    }

    @Test
    void create_whenBookIsValid_thenReturnsBookWithId() {
        Book savedBook = createBook("Title", "123", LocalDate.now(), Set.of(createAuthor("Author")));
        assertNotNull(savedBook.id());
    }

    @Test
    void findById_whenBookExists_thenReturnsBook() {
        Book savedBook = createBook("Title", "123", LocalDate.now(), Set.of(createAuthor("Author")));
        var result = adapter.findById(savedBook.id());
        assertTrue(result.isPresent());
        assertEquals(savedBook.title(), result.get().title());
    }

    @Test
    void findById_whenBookDoesNotExist_thenReturnsEmpty() {
        var result = adapter.findById(UUID.randomUUID());
        assertTrue(result.isEmpty());
    }

    @Test
    void update_whenBookIsValid_thenUpdatesBook() {
        Book savedBook = createBook("Title", "123", LocalDate.now(), Set.of(createAuthor("Author")));
        Book updated = new Book(savedBook.id(), "Updated", "456", LocalDate.now(), savedBook.authors());
        Book result = adapter.update(updated);
        assertEquals("Updated", result.title());
        assertEquals("456", result.isbn());
    }

    @Test
    void delete_whenBookDoesNotExist_thenDoesNotThrow() {
        UUID nonExistentId = UUID.randomUUID();
        assertDoesNotThrow(() -> adapter.delete(nonExistentId));
    }

    @Test
    void delete_whenBookExists_thenRemovesBook() {
        Book savedBook = createBook("Title", "123", LocalDate.now(), Set.of(createAuthor("Author")));
        adapter.delete(savedBook.id());
        assertTrue(adapter.findById(savedBook.id()).isEmpty());
    }

    @Test
    void findAllByAuthorId_whenBooksExist_thenReturnsCorrectBooks() {
        Author author = createAuthor("Author A");
        createBook("A Book", "123", LocalDate.now(), Set.of(author));
        Set<Book> result = adapter.findAllByAuthorId(author.id());
        assertFalse(result.isEmpty());
        assertTrue(result.iterator().next().authors().contains(author));
    }

    @Test
    void findAll_whenBooksExist_thenReturnsAllBooks() {
        for (int i = 0; i < 3; i++) {
            Author savedAuthor = createAuthor("Author " + i);
            createBook("Title " + i, "ISBN-" + i, LocalDate.now(), Set.of(savedAuthor));
        }
        Set<Book> books = adapter.findAll();
        assertEquals(3, books.size());
    }

    @Test
    void findAll_whenNoBooksExist_thenReturnsEmptySet() {
        Set<Book> books = adapter.findAll();
        assertTrue(books.isEmpty());
    }
}
