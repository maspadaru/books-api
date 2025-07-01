package org.mspadaru.books.infrastructure.web.mapper;

import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.infrastructure.web.dto.AuthorDto;

public class AuthorDtoMapper {

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.id(), author.name());
    }

    public static Author toDomain(AuthorDto dto) {
        return new Author(dto.id(), dto.name());
    }
}
