package org.mspadaru.books.application.service;

import org.mspadaru.books.application.port.in.BookService;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.domain.port.out.BookRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class DefaultBookService implements BookService {

    private final BookRepository repository;

    public DefaultBookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Book> findAllBooks() {
        return Set.of();
    }

    @Override
    public Set<Book> findAllBooksByAuthorId(UUID authorId) {
        return Set.of();
    }

    @Override
    public Optional<Book> findBookById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Book createBook(Book book) {
        return repository.create(book);
    }

    @Override
    public Optional<Book> updateBook(UUID id, Book book) {
        return Optional.empty();
    }

    @Override
    public boolean deleteBook(UUID id) {
        return false;
    }

    @Override
    public Optional<Book> assignAuthorToBook(UUID authorId, UUID bookId) {
        return Optional.empty();
    }
}
