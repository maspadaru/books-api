package org.mspadaru.books.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record BookDto(UUID id, String title, String isbn, LocalDate publishedDate, Set<AuthorDto> authors) {
}
