package org.mspadaru.books.domain.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record Book(UUID id, String title, String isbn, LocalDate publishedDate, Set<Author> authors) {
    public Book {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title is required");
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("ISBN is required");
        if (publishedDate == null) throw new IllegalArgumentException("Published date is required");
        if (authors == null || authors.isEmpty()) throw new IllegalArgumentException("At least one author is required");
    }
}
