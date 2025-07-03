package org.mspadaru.books.infrastructure.web.mapper;

import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.infrastructure.web.dto.AuthorDto;
import org.mspadaru.books.infrastructure.web.dto.BookDto;

import java.util.Set;
import java.util.stream.Collectors;

public class BookDtoMapper {

    private BookDtoMapper() {}

    public static BookDto toDto(Book book) {
        Set<AuthorDto> authorDtos = book.authors().stream().map(AuthorDtoMapper::toDto).collect(Collectors.toSet());
        return new BookDto(book.id(), book.title(), book.isbn(), book.publishedDate(), authorDtos);
    }

    public static Book toDomain(BookDto bookDto) {
        Set<Author> authors = bookDto.authors().stream().map(AuthorDtoMapper::toDomain).collect(Collectors.toSet());
        return new Book(bookDto.id(), bookDto.title(), bookDto.isbn(), bookDto.publishedDate(), authors);
    }
}
