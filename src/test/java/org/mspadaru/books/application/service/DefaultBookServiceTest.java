package org.mspadaru.books.application.service;

import org.junit.jupiter.api.Test;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.domain.port.out.AuthorRepository;
import org.mspadaru.books.domain.port.out.BookRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultBookServiceTest {

    @Test
    void createBook_whenBookIsValid_thenReturnsBookWithId() {
        BookRepository mockRepository = mock(BookRepository.class);
        DefaultBookService service = new DefaultBookService(mockRepository, mock(AuthorRepository.class));
        Book inputBook = new Book(null, "Test Book", "1234567890", LocalDate.now(), Set.of());
        Book savedBook = new Book(UUID.randomUUID(), "Test Book", "1234567890", LocalDate.now(), Set.of());
        when(mockRepository.create(any(Book.class))).thenReturn(savedBook);
        Book result = service.createBook(inputBook);
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(savedBook, result);
    }

    @Test
    void findBookById_whenBookIsPresent_thenReturnsBookWithId() {
        BookRepository mockRepository = mock(BookRepository.class);
        DefaultBookService service = new DefaultBookService(mockRepository, mock(AuthorRepository.class));
        Book testBook = new Book(UUID.randomUUID(), "Test Book", "1234567890", LocalDate.now(), Set.of());
        when(mockRepository.findById(testBook.id())).thenReturn(Optional.of(testBook));
        Optional<Book> result = service.findBookById(testBook.id());
        assertTrue(result.isPresent());
        assertEquals(testBook, result.get());
    }

    @Test
    void findAllBooks_whenBooksArePresent_thenReturnsBookSet() {
        BookRepository mockRepository = mock(BookRepository.class);
        DefaultBookService service = new DefaultBookService(mockRepository, mock(AuthorRepository.class));
        Book firstBook = new Book(UUID.randomUUID(), "First Book", "1234567890", LocalDate.now(), Set.of());
        Book secondBook = new Book(UUID.randomUUID(), "Second Book", "9087654321", LocalDate.now(), Set.of());
        Set<Book> testSet = Set.of(firstBook, secondBook);
        when(mockRepository.findAll()).thenReturn(testSet);
        Set<Book> result = service.findAllBooks();
        assertEquals(testSet, result);
    }

    @Test
    void updateBook_whenBookIsPresent_thenReturnsUpdatedBook() {
        BookRepository mockRepository = mock(BookRepository.class);
        DefaultBookService service = new DefaultBookService(mockRepository, mock(AuthorRepository.class));
        Book testBook = new Book(UUID.randomUUID(), "Test Book", "1234567890", LocalDate.now(), Set.of());
        when(mockRepository.update(testBook.id(), testBook)).thenReturn(Optional.of(testBook));
        Optional<Book> result = service.updateBook(testBook.id(), testBook);
        assertTrue(result.isPresent());
        assertEquals(testBook, result.get());
    }

    @Test
    void deleteBook_whenBookIsPresent_thenReturnsTrue() {
        BookRepository mockRepository = mock(BookRepository.class);
        DefaultBookService service = new DefaultBookService(mockRepository, mock(AuthorRepository.class));
        UUID id = UUID.randomUUID();
        when(mockRepository.delete(id)).thenReturn(true);
        boolean result = service.deleteBook(id);
        assertTrue(result);
    }

    @Test
    void findAllBooksByAuthorId_whenBooksArePresent_thenReturnsBookSet() {
        BookRepository mockRepository = mock(BookRepository.class);
        DefaultBookService service = new DefaultBookService(mockRepository, mock(AuthorRepository.class));
        Author testAuthor = new Author(UUID.randomUUID(), "Test Author");
        Book firstBook = new Book(UUID.randomUUID(), "First Book", "1234567890", LocalDate.now(), Set.of(testAuthor));
        Book secondBook = new Book(UUID.randomUUID(), "Second Book", "9087654321", LocalDate.now(), Set.of(testAuthor));
        Set<Book> testSet = Set.of(firstBook, secondBook);
        when(mockRepository.findAllByAuthorId(testAuthor.id())).thenReturn(testSet);
        Set<Book> result = service.findAllBooksByAuthorId(testAuthor.id());
        assertEquals(testSet.size(), result.size());
        assertTrue(result.stream().allMatch(book -> book.authors().contains(testAuthor)));
    }

    @Test
    void assignAuthorToBook_whenBookIsPresent_thenReturnsUpdatedBook() {
        BookRepository bookRepository = mock(BookRepository.class);
        AuthorRepository authorRepository = mock(AuthorRepository.class);
        DefaultBookService service = new DefaultBookService(bookRepository, authorRepository);
        Author testAuthor = new Author(UUID.randomUUID(), "Test Author");
        Book initialBook = new Book(UUID.randomUUID(), "Test Book", "1234567890", LocalDate.now(), Set.of());
        Book updatedBook = new Book(initialBook.id(), "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));
        when(bookRepository.findById(initialBook.id())).thenReturn(Optional.of(initialBook));
        when(authorRepository.findById(testAuthor.id())).thenReturn(Optional.of(testAuthor));
        when(bookRepository.update(eq(initialBook.id()), any(Book.class))).thenReturn(Optional.of(updatedBook));
        Optional<Book> result = service.assignAuthorToBook(testAuthor.id(), initialBook.id());
        assertTrue(result.isPresent());
        assertTrue(result.get().authors().contains(testAuthor));
    }

}