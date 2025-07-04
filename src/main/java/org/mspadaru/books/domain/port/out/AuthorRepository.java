package org.mspadaru.books.domain.port.out;

import org.mspadaru.books.domain.model.Author;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AuthorRepository {

    Set<Author> findAll();

    Optional<Author> findById(UUID id);

    Author create(Author author);

    Author update(Author author);

    void delete(UUID id);

}
