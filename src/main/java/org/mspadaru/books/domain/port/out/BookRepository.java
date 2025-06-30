package org.mspadaru.books.domain.port.out;

import org.mspadaru.books.domain.model.Book;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BookRepository {

    Set<Book> findAll();

    Set<Book> findAllByAuthorId(UUID authorId);

    Optional<Book> findById(UUID id);

    Book create(Book book);

    Book update(Book book);

    void delete(UUID id);

}
