package org.mspadaru.books.infrastructure.persistence.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mspadaru.books.domain.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class AuthorJpaAdapterIntegrationTest {

    @Autowired
    AuthorJpaAdapter authorJpaAdapter;

    @Test
    void findAll_whenAuthorsExist_thenReturnsAllAuthors() {
        Author author1 = new Author(null, "First Author");
        Author author2 = new Author(null, "Second Author");
        Author author3 = new Author(null, "Third Author");
        authorJpaAdapter.create(author1);
        authorJpaAdapter.create(author2);
        authorJpaAdapter.create(author3);
        Set<Author> result = authorJpaAdapter.findAll();
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(author -> author.name().equals(author1.name())));
    }

    @Test
    void create_whenAuthorIsValid_theReturnsAuthorWithId() {
        Author author = new Author(null, "Test Author");
        Author savedAuthor = authorJpaAdapter.create(author);
        assertNotNull(savedAuthor.id());
        assertEquals(author.name(), savedAuthor.name());
    }
}