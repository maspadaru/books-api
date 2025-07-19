package org.mspadaru.books.domain.port.out;

import org.mspadaru.books.domain.model.Book;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Port for accessing and modifying book data in the persistence layer.
 * Implemented by the infrastructure layer.
 */
public interface BookRepository {

    /**
     * Returns all books available in the system.
     *
     * @return a set of all books
     */
    Set<Book> findAll();

    /**
     * Finds all books associated with a given author.
     *
     * @param authorId the UUID of the author
     * @return a set of books written by the given author
     */
    Set<Book> findAllByAuthorId(UUID authorId);

    /**
     * Finds a book by its unique identifier.
     *
     * @param id the UUID of the book
     * @return an Optional containing the book if found, or empty if not
     */
    Optional<Book> findById(UUID id);

    /**
     * Persists a new book.
     *
     * @param book the book to create
     * @return the created book with a generated ID
     */
    Book create(Book book);

    /**
     * Updates an existing book.
     *
     * @param book the book to update (must already exist)
     * @return the updated book
     */
    Book update(Book book);

    /**
     * Deletes a book by its ID.
     *
     * @param id the UUID of the book to delete
     */
    void delete(UUID id);
}
