package org.mspadaru.books.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.port.out.AuthorRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultAuthorServiceTest {

    @Mock
    private AuthorRepository mockRepository;

    @InjectMocks
    private DefaultAuthorService service;

    @Test
    void findAllAuthors_whenAuthorsArePresent_thenReturnsAuthorSet() {
        Author firstAuthor = new Author(UUID.randomUUID(), "First Author");
        Author secondAuthor = new Author(UUID.randomUUID(), "Second Author");
        Set<Author> testSet = Set.of(firstAuthor, secondAuthor);

        when(mockRepository.findAll()).thenReturn(testSet);

        Set<Author> result = service.findAllAuthors();
        assertEquals(testSet, result);
    }

    @Test
    void findAuthorById_whenAuthorIsPresent_thenReturnsAuthor() {
        Author testAuthor = new Author(UUID.randomUUID(), "Test Author");

        when(mockRepository.findById(testAuthor.id())).thenReturn(Optional.of(testAuthor));

        Optional<Author> result = service.findAuthorById(testAuthor.id());
        assertTrue(result.isPresent());
        assertEquals(testAuthor, result.get());
    }

    @Test
    void createAuthor_whenAuthorIsValid_thenReturnsAuthorWithId() {
        Author inputAuthor = new Author(null, "Test Author");
        Author savedAuthor = new Author(UUID.randomUUID(), "Test Author");

        when(mockRepository.create(any(Author.class))).thenReturn(savedAuthor);

        Author result = service.createAuthor(inputAuthor);
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(savedAuthor, result);
    }

    @Test
    void createAuthor_whenAuthorIsNull_thenThrowsException() {
        assertThrows(NullPointerException.class, () -> service.createAuthor(null));
    }

    @Test
    void updateAuthor_whenAuthorIsPresent_thenReturnsUpdatedAuthor() {
        UUID id = UUID.randomUUID();
        Author inputAuthor = new Author(null, "Updated Author");
        Author existingAuthor = new Author(id, "Old Author");
        Author updatedAuthor = new Author(id, "Updated Author");

        when(mockRepository.findById(id)).thenReturn(Optional.of(existingAuthor));
        when(mockRepository.update(updatedAuthor)).thenReturn(updatedAuthor);

        Optional<Author> result = service.updateAuthor(id, inputAuthor);
        assertTrue(result.isPresent());
        assertEquals(updatedAuthor, result.get());
    }

    @Test
    void updateAuthor_whenAuthorIsNotFound_thenReturnsEmptyOptional() {
        UUID id = UUID.randomUUID();
        Author inputAuthor = new Author(null, "New Author");

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Author> result = service.updateAuthor(id, inputAuthor);
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteAuthor_whenAuthorIsPresent_thenReturnsTrue() {
        Author testAuthor = new Author(UUID.randomUUID(), "Test Author");

        when(mockRepository.findById(testAuthor.id())).thenReturn(Optional.of(testAuthor));

        boolean result = service.deleteAuthor(testAuthor.id());
        assertTrue(result);
    }

    @Test
    void deleteAuthor_whenAuthorIsNotFound_thenReturnsFalse() {
        UUID id = UUID.randomUUID();

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = service.deleteAuthor(id);
        assertFalse(result);
    }
}
