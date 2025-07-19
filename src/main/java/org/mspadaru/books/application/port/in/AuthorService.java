package org.mspadaru.books.application.port.in;

import org.mspadaru.books.domain.model.Author;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Application service port for managing authors.
 * Defines use cases for retrieving, creating, updating, and deleting authors.
 * Acts as the entry point from the web layer to the application layer.
 */
public interface AuthorService {

    /**
     * Retrieves all authors in the system.
     *
     * @return a set of all authors
     */
    Set<Author> findAllAuthors();

    /**
     * Retrieves a specific author by their ID.
     *
     * @param id the UUID of the author to retrieve
     * @return an Optional containing the author if found, or empty otherwise
     */
    Optional<Author> findAuthorById(UUID id);

    /**
     * Creates a new author.
     *
     * @param author the author to create (ID may be null)
     * @return the created author with a generated ID
     */
    Author createAuthor(Author author);

    /**
     * Updates an existing author.
     *
     * @param id     the ID of the author to update
     * @param author the new author data
     * @return an Optional containing the updated author if the ID was found, or empty otherwise
     */
    Optional<Author> updateAuthor(UUID id, Author author);

    /**
     * Deletes an author by their ID.
     *
     * @param id the UUID of the author to delete
     * @return true if the author existed and was deleted, false otherwise
     */
    boolean deleteAuthor(UUID id);
}
