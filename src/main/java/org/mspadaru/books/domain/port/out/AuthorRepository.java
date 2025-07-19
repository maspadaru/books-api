package org.mspadaru.books.domain.port.out;

import org.mspadaru.books.domain.model.Author;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Port for accessing and modifying author data in the persistence layer.
 * Implemented by the infrastructure layer.
 */
public interface AuthorRepository {

    /**
     * Returns all authors available in the system.
     *
     * @return a set of all authors
     */
    Set<Author> findAll();

    /**
     * Finds an author by its unique identifier.
     *
     * @param id the UUID of the author
     * @return an Optional containing the author if found, or empty if not
     */
    Optional<Author> findById(UUID id);

    /**
     * Persists a new author.
     *
     * @param author the author to create
     * @return the created author with a generated ID
     */
    Author create(Author author);

    /**
     * Updates an existing author.
     *
     * @param author the author to update (must already exist)
     * @return the updated author
     */
    Author update(Author author);

    /**
     * Deletes an author by its ID.
     *
     * @param id the UUID of the author to delete
     */
    void delete(UUID id);
}
