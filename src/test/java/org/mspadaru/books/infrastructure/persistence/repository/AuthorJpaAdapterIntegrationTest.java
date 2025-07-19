package org.mspadaru.books.infrastructure.persistence.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mspadaru.books.domain.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class AuthorJpaAdapterIntegrationTest {

    @Autowired
    AuthorJpaAdapter authorJpaAdapter;

    @Test
    void findAll_whenAuthorsExist_thenReturnsAllAuthors() {
        Author a1 = new Author(null, "First Author");
        Author a2 = new Author(null, "Second Author");
        authorJpaAdapter.create(a1);
        authorJpaAdapter.create(a2);

        Set<Author> result = authorJpaAdapter.findAll();
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(a -> a.name().equals("First Author")));
    }

    @Test
    void findAll_whenNoAuthorsExist_thenReturnsEmptySet() {
        Set<Author> result = authorJpaAdapter.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void create_whenAuthorIsValid_thenReturnsAuthorWithId() {
        Author author = new Author(null, "Test Author");
        Author saved = authorJpaAdapter.create(author);

        assertNotNull(saved.id());
        assertEquals("Test Author", saved.name());
    }

    @Test
    void findById_whenAuthorExists_thenReturnsAuthor() {
        Author saved = authorJpaAdapter.create(new Author(null, "Findable Author"));
        Optional<Author> result = authorJpaAdapter.findById(saved.id());

        assertTrue(result.isPresent());
        assertEquals("Findable Author", result.get().name());
    }

    @Test
    void findById_whenAuthorDoesNotExist_thenReturnsEmptyOptional() {
        UUID randomId = UUID.randomUUID();
        Optional<Author> result = authorJpaAdapter.findById(randomId);

        assertTrue(result.isEmpty());
    }

    @Test
    void update_whenAuthorExists_thenReturnsUpdatedAuthor() {
        Author original = authorJpaAdapter.create(new Author(null, "Original"));
        Author updated = new Author(original.id(), "Updated");

        Author result = authorJpaAdapter.update(updated);

        assertEquals(original.id(), result.id());
        assertEquals("Updated", result.name());
    }

    @Test
    void delete_whenAuthorExists_thenAuthorIsDeleted() {
        Author author = authorJpaAdapter.create(new Author(null, "To Be Deleted"));

        authorJpaAdapter.delete(author.id());

        Optional<Author> result = authorJpaAdapter.findById(author.id());
        assertTrue(result.isEmpty());
    }

    @Test
    void delete_whenAuthorDoesNotExist_thenDoesNothing() {
        UUID randomId = UUID.randomUUID();

        assertDoesNotThrow(() -> authorJpaAdapter.delete(randomId));
    }
}
