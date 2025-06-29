package org.mspadaru.books.infrastructure.persistence.mapper;

import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.infrastructure.persistence.entity.AuthorEntity;

public class AuthorMapper {

    public static Author toDomain(AuthorEntity entity) {
        return new Author(entity.getId(), entity.getName());
    }

    public static AuthorEntity toEntity(Author author) {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(author.id());
        entity.setName(author.name());
        return entity;
    }

}
