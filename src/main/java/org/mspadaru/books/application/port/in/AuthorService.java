package org.mspadaru.books.application.port.in;

import org.mspadaru.books.domain.model.Author;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AuthorService {

    Set<Author> findAllAuthors();

    Optional<Author> findAuthorById(UUID id);

    Author createAuthor(Author author);

    Optional<Author> updateAuthor(UUID id, Author author);

    boolean deleteAuthor(UUID id);

}
