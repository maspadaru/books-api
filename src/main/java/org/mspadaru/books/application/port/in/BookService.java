package org.mspadaru.books.application.port.in;

import org.mspadaru.books.domain.model.Book;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Application service port for managing books.
 * Defines use cases for retrieving, creating, updating, deleting books, and assigning authors.
 * Serves as the interface between the web layer and application logic.
 */
public interface BookService {

    /**
     * Retrieves all books in the system.
     *
     * @return a set of all books
     */
    Set<Book> findAllBooks();

    /**
     * Retrieves all books written by a specific author.
     *
     * @param authorId the UUID of the author
     * @return a set of books associated with the given author
     */
    Set<Book> findAllBooksByAuthorId(UUID authorId);

    /**
     * Retrieves a specific book by its ID.
     *
     * @param id the UUID of the book
     * @return an Optional containing the book if found, or empty otherwise
     */
    Optional<Book> findBookById(UUID id);

    /**
     * Creates a new book.
     *
     * @param book the book to create (ID may be null)
     * @return the created book with a generated ID
     */
    Book createBook(Book book);

    /**
     * Updates an existing book.
     *
     * @param id   the ID of the book to update
     * @param book the new book data
     * @return an Optional containing the updated book if the ID was found, or empty otherwise
     */
    Optional<Book> updateBook(UUID id, Book book);

    /**
     * Deletes a book by its ID.
     *
     * @param id the UUID of the book to delete
     * @return true if the book existed and was deleted, false otherwise
     */
    boolean deleteBook(UUID id);

    /**
     * Assigns an author to an existing book.
     * Adds the author to the book’s set of authors if both exist.
     *
     * @param authorId the ID of the author to assign
     * @param bookId   the ID of the book to update
     * @return an Optional containing the updated book, or empty if book or author was not found
     */
    Optional<Book> assignAuthorToBook(UUID authorId, UUID bookId);
}
