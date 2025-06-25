package org.mspadaru.books.application.port.in;

import org.mspadaru.books.domain.model.Book;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BookService {

    Set<Book> findAllBooks();

    Set<Book> findAllBooksByAuthorId(UUID authorId);

    Optional<Book> findBookById(UUID id);

    Book createBook(Book book);

    Optional<Book> updateBook(UUID id, Book book);

    boolean deleteBook(UUID id);

    Optional<Book> assignAuthorToBook(UUID authorId, UUID bookId);

}
