package org.mspadaru.books.application.service;

import org.junit.jupiter.api.Test;
import org.mspadaru.books.application.port.in.AuthorService;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.port.out.AuthorRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultAuthorServiceTest {

    @Test
    void findAllAuthors_whenAuthorsArePresent_thenReturnsAuthorSet() {
        AuthorRepository mockRepository = mock(AuthorRepository.class);
        AuthorService service = new DefaultAuthorService(mockRepository);
        Author firstAuthor = new Author(UUID.randomUUID(), "First Author");
        Author secondAuthor = new Author(UUID.randomUUID(), "Second Author");
        Set<Author> testSet = Set.of(firstAuthor, secondAuthor);
        when(mockRepository.findAll()).thenReturn(testSet);
        Set<Author> result = service.findAllAuthors();
        assertEquals(testSet, result);
    }

    @Test
    void findAuthorById_whenAuthorIsPresent_theReturnsAuthor() {
        AuthorRepository mockRepository = mock(AuthorRepository.class);
        AuthorService service = new DefaultAuthorService(mockRepository);
        Author testAuthor = new Author(UUID.randomUUID(), "Test Author");
        when(mockRepository.findById(testAuthor.id())).thenReturn(Optional.of(testAuthor));
        Optional<Author> result = service.findAuthorById(testAuthor.id());
        assertTrue(result.isPresent());
        assertEquals(testAuthor, result.get());
    }

    @Test
    void createAuthor_whenAuthorIsValid_theReturnsAuthorWithId() {
        AuthorRepository mockRepository = mock(AuthorRepository.class);
        AuthorService service = new DefaultAuthorService(mockRepository);
        Author inputAuthor = new Author(null, "Test Author");
        Author savedAuthor = new Author(UUID.randomUUID(), "Test Author");
        when(mockRepository.create(any(Author.class))).thenReturn(savedAuthor);
        Author result = service.createAuthor(inputAuthor);
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(savedAuthor, result);
    }

    @Test
    void updateAuthor_whenAuthorIsPresent_theReturnsUpdatedAuthor() {
        AuthorRepository mockRepository = mock(AuthorRepository.class);
        AuthorService service = new DefaultAuthorService(mockRepository);
        UUID id = UUID.randomUUID();
        Author inputAuthor = new Author(null, "Test Author");
        Author testAuthor = new Author(id, "Test Author");
        when(mockRepository.update(testAuthor)).thenReturn(testAuthor);
        when(mockRepository.findById(id)).thenReturn(Optional.of(testAuthor));
        Optional<Author> result = service.updateAuthor(id, inputAuthor);
        assertTrue(result.isPresent());
        assertEquals(testAuthor, result.get());
    }

    @Test
    void deleteAuthor_whenAuthorIsPresent_thenReturnsTrue() {
        AuthorRepository mockRepository = mock(AuthorRepository.class);
        AuthorService service = new DefaultAuthorService(mockRepository);
        Author testAuthor = new Author(UUID.randomUUID(), "Test Author");
        when(mockRepository.findById(testAuthor.id())).thenReturn(Optional.of(testAuthor));
        boolean result = service.deleteAuthor(testAuthor.id());
        assertTrue(result);
    }

}