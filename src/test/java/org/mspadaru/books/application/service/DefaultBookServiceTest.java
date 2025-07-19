package org.mspadaru.books.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.domain.port.out.AuthorRepository;
import org.mspadaru.books.domain.port.out.BookRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private DefaultBookService service;

    private final Author testAuthor = new Author(UUID.randomUUID(), "George Orwell");

    @Test
    void createBook_whenBookIsValid_thenReturnsBookWithId() {
        Book inputBook = new Book(null, "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));
        Book savedBook = new Book(UUID.randomUUID(), "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));

        when(bookRepository.create(any(Book.class))).thenReturn(savedBook);

        Book result = service.createBook(inputBook);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(savedBook, result);
    }

    @Test
    void findBookById_whenBookIsPresent_thenReturnsBookWithId() {
        Book testBook = new Book(UUID.randomUUID(), "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));

        when(bookRepository.findById(testBook.id())).thenReturn(Optional.of(testBook));

        Optional<Book> result = service.findBookById(testBook.id());

        assertTrue(result.isPresent());
        assertEquals(testBook, result.get());
    }

    @Test
    void findAllBooks_whenBooksArePresent_thenReturnsBookSet() {
        Book firstBook = new Book(UUID.randomUUID(), "First Book", "1234567890", LocalDate.now(), Set.of(testAuthor));
        Book secondBook = new Book(UUID.randomUUID(), "Second Book", "9087654321", LocalDate.now(), Set.of(testAuthor));
        Set<Book> testSet = Set.of(firstBook, secondBook);

        when(bookRepository.findAll()).thenReturn(testSet);

        Set<Book> result = service.findAllBooks();

        assertEquals(testSet, result);
    }

    @Test
    void findAllBooksByAuthorId_whenBooksArePresent_thenReturnsBookSet() {
        Book firstBook = new Book(UUID.randomUUID(), "First Book", "1234567890", LocalDate.now(), Set.of(testAuthor));
        Book secondBook = new Book(UUID.randomUUID(), "Second Book", "9087654321", LocalDate.now(), Set.of(testAuthor));
        Set<Book> testSet = Set.of(firstBook, secondBook);

        when(bookRepository.findAllByAuthorId(testAuthor.id())).thenReturn(testSet);

        Set<Book> result = service.findAllBooksByAuthorId(testAuthor.id());

        assertEquals(testSet.size(), result.size());
        assertTrue(result.stream().allMatch(book -> book.authors().contains(testAuthor)));
    }

    @Test
    void updateBook_whenBookIsPresent_thenReturnsUpdatedBook() {
        UUID id = UUID.randomUUID();
        Book inputBook = new Book(null, "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));
        Book updatedBook = new Book(id, "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));

        when(bookRepository.findById(id)).thenReturn(Optional.of(updatedBook));
        when(bookRepository.update(updatedBook)).thenReturn(updatedBook);

        Optional<Book> result = service.updateBook(id, inputBook);

        assertTrue(result.isPresent());
        assertEquals(updatedBook, result.get());
    }

    @Test
    void updateBook_whenBookNotFound_thenReturnsEmptyOptional() {
        UUID id = UUID.randomUUID();
        Book inputBook = new Book(null, "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Book> result = service.updateBook(id, inputBook);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteBook_whenBookIsPresent_thenReturnsTrue() {
        Book book = new Book(UUID.randomUUID(), "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));

        when(bookRepository.findById(book.id())).thenReturn(Optional.of(book));

        boolean result = service.deleteBook(book.id());

        assertTrue(result);
    }

    @Test
    void deleteBook_whenBookNotFound_thenReturnsFalse() {
        UUID id = UUID.randomUUID();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = service.deleteBook(id);

        assertFalse(result);
    }

    @Test
    void assignAuthorToBook_whenBookAndAuthorExist_thenReturnsUpdatedBook() {
        Author newAuthor = new Author(UUID.randomUUID(), "New Author");
        Book initialBook = new Book(UUID.randomUUID(), "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));

        Set<Author> expectedAuthors = new HashSet<>(initialBook.authors());
        expectedAuthors.add(newAuthor);

        Book updatedBook = new Book(initialBook.id(), initialBook.title(), initialBook.isbn(),
                initialBook.publishedDate(), expectedAuthors);

        when(bookRepository.findById(initialBook.id())).thenReturn(Optional.of(initialBook));
        when(authorRepository.findById(newAuthor.id())).thenReturn(Optional.of(newAuthor));
        when(bookRepository.update(any(Book.class))).thenReturn(updatedBook);

        Optional<Book> result = service.assignAuthorToBook(newAuthor.id(), initialBook.id());

        assertTrue(result.isPresent());
        assertTrue(result.get().authors().contains(newAuthor));
        assertTrue(result.get().authors().contains(testAuthor));
    }

    @Test
    void assignAuthorToBook_whenBookNotFound_thenReturnsEmptyOptional() {
        UUID bookId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Optional<Book> result = service.assignAuthorToBook(authorId, bookId);

        assertTrue(result.isEmpty());
    }

    @Test
    void assignAuthorToBook_whenAuthorNotFound_thenReturnsEmptyOptional() {
        UUID bookId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Book book = new Book(bookId, "Test Book", "1234567890", LocalDate.now(), Set.of(testAuthor));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        Optional<Book> result = service.assignAuthorToBook(authorId, bookId);

        assertTrue(result.isEmpty());
    }

    @Test
    void assignAuthorToBook_whenAuthorAlreadyAssigned_thenDoesNotDuplicateAuthor() {
        Book bookWithAuthor = new Book(UUID.randomUUID(), "Title", "isbn", LocalDate.now(), Set.of(testAuthor));

        when(bookRepository.findById(bookWithAuthor.id())).thenReturn(Optional.of(bookWithAuthor));
        when(authorRepository.findById(testAuthor.id())).thenReturn(Optional.of(testAuthor));
        when(bookRepository.update(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Book> result = service.assignAuthorToBook(testAuthor.id(), bookWithAuthor.id());

        assertTrue(result.isPresent());
        assertEquals(1, result.get().authors().size());
    }
}
