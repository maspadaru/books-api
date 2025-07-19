package org.mspadaru.books.infrastructure.web.api;

import jakarta.validation.Valid;
import org.mspadaru.books.application.port.in.BookService;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.infrastructure.web.dto.BookDto;
import org.mspadaru.books.infrastructure.web.dto.BookRequest;
import org.mspadaru.books.infrastructure.web.mapper.BookDtoMapper;
import org.mspadaru.books.infrastructure.web.mapper.BookRequestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Set<BookDto>> getAllBooks() {
        Set<BookDto> books = bookService.findAllBooks().stream().map(BookDtoMapper::toDto).collect(Collectors.toSet());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable UUID id) {
        return bookService.findBookById(id).map(BookDtoMapper::toDto).map(ResponseEntity::ok).orElse(
                ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookRequest request) {
        Book book = BookRequestMapper.toDomain(request);
        Book savedBook = bookService.createBook(book);
        URI location = URI.create("/api/authors/" + savedBook.id());
        return ResponseEntity.created(location).body(BookDtoMapper.toDto(savedBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable UUID id, @Valid @RequestBody BookRequest request) {
        Book bookToUpdate = BookRequestMapper.toDomain(request);
        return bookService.updateBook(id, bookToUpdate).map(BookDtoMapper::toDto).map(ResponseEntity::ok).orElse(
                ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID id) {
        if (!bookService.deleteBook(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-author/{authorId}")
    public ResponseEntity<Set<BookDto>> getBooksByAuthorId(@PathVariable UUID authorId) {
        Set<BookDto> books = bookService.findAllBooksByAuthorId(authorId).stream().map(BookDtoMapper::toDto).collect(
                Collectors.toSet());
        return ResponseEntity.ok(books);
    }

}
