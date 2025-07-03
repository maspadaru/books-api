package org.mspadaru.books.infrastructure.web.mapper;

import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.infrastructure.web.dto.BookRequest;

import java.util.Set;
import java.util.stream.Collectors;

public class BookRequestMapper {

    private BookRequestMapper() {
    }

    public static Book toDomain(BookRequest bookRequest) {
        Set<Author> authors = bookRequest.authors().stream().map(AuthorDtoMapper::toDomain).collect(Collectors.toSet());
        return new Book(null, bookRequest.title(), bookRequest.isbn(), bookRequest.publishedDate(), authors);
    }

}
