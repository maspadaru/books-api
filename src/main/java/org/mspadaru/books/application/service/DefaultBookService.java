package org.mspadaru.books.application.service;

import org.mspadaru.books.application.port.in.BookService;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.domain.port.out.AuthorRepository;
import org.mspadaru.books.domain.port.out.BookRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class DefaultBookService implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public DefaultBookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Set<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Set<Book> findAllBooksByAuthorId(UUID authorId) {
        return bookRepository.findAllByAuthorId(authorId);
    }

    @Override
    public Optional<Book> findBookById(UUID id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.create(book);
    }

    @Override
    public Optional<Book> updateBook(UUID id, Book book) {
        return bookRepository.update(id, book);
    }

    @Override
    public boolean deleteBook(UUID id) {
        return bookRepository.delete(id);
    }

    @Override
    public Optional<Book> assignAuthorToBook(UUID authorId, UUID bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            return Optional.empty();
        }
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            return Optional.empty();
        }
        Set<Author> authorSet = new HashSet<>(book.get().authors());
        authorSet.add(author.get());
        Book updatedBook = new Book(bookId, book.get().title(), book.get().isbn(), book.get().publishedDate(),
                authorSet);
        return bookRepository.update(bookId, updatedBook);
    }

}
