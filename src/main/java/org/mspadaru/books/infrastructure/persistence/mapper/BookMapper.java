package org.mspadaru.books.infrastructure.persistence.mapper;

import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.infrastructure.persistence.entity.AuthorEntity;
import org.mspadaru.books.infrastructure.persistence.entity.BookEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class BookMapper {

    public static Book toDomain(BookEntity entity) {
        Set<Author> authorSet = entity.getAuthors().stream().map(AuthorMapper::toDomain).collect(Collectors.toSet());
        return new Book(entity.getId(), entity.getTitle(), entity.getIsbn(), entity.getPublishedDate(), authorSet);
    }

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
