package org.mspadaru.books.infrastructure.persistence.mapper;

import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.infrastructure.persistence.entity.AuthorEntity;

/**
 * Utility class for converting between Author domain model and persistence entity.
 */
public final class AuthorMapper {

    private AuthorMapper() {
        // Prevent instantiation
    }

    /**
     * Converts a JPA AuthorEntity to a domain Author.
     *
     * @param entity the JPA entity
     * @return the domain model
     */
    public static Author toDomain(AuthorEntity entity) {
        return new Author(entity.getId(), entity.getName());
    }

    /**
     * Converts a domain Author to a JPA AuthorEntity.
     *
     * @param author the domain model
     * @return the JPA entity
     */
    public static AuthorEntity toEntity(Author author) {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(author.id());
        entity.setName(author.name());
        return entity;
    }

}
