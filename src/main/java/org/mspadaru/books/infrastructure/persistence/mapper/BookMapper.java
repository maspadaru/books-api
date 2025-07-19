package org.mspadaru.books.infrastructure.persistence.mapper;

import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.infrastructure.persistence.entity.AuthorEntity;
import org.mspadaru.books.infrastructure.persistence.entity.BookEntity;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for converting between Book domain model and persistence entity.
 */
public final class BookMapper {

    private BookMapper() {
        // Prevent instantiation
    }

    /**
     * Converts a JPA BookEntity to a domain Book.
     *
     * @param entity the JPA entity
     * @return the domain model
     */
    public static Book toDomain(BookEntity entity) {
        Set<Author> authorSet = entity.getAuthors().stream().map(AuthorMapper::toDomain).collect(Collectors.toSet());

        return new Book(entity.getId(), entity.getTitle(), entity.getIsbn(), entity.getPublishedDate(), authorSet);
    }

    /**
     * Converts a domain Book to a JPA BookEntity.
     *
     * @param book the domain model
     * @return the JPA entity
     */
    public static BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.id());
        entity.setTitle(book.title());
        entity.setIsbn(book.isbn());
        entity.setPublishedDate(book.publishedDate());
        Set<AuthorEntity> authorEntitySet = book.authors().stream().map(AuthorMapper::toEntity).collect(
                Collectors.toSet());
        entity.setAuthors(authorEntitySet);
        return entity;
    }
}
