package org.mspadaru.books.domain.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record Book(UUID id, String title, String isbn, LocalDate publishedDate, Set<Author> authors) {
}
