package org.mspadaru.books.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.mspadaru.books.domain.model.constraints.BookConstraints;

import java.time.LocalDate;
import java.util.Set;

public record BookRequest(

        @Size(max = BookConstraints.TITLE_MAX_LENGTH, message = BookConstraints.TITLE_SIZE_MESSAGE) @NotBlank(message = "Title must not be blank") String title,

        @Size(max = BookConstraints.ISBN_MAX_LENGTH, message = BookConstraints.ISBN_SIZE_MESSAGE) @NotBlank(message = "ISBN must have an ISBN") String isbn,

        @PastOrPresent(message = "Published date must not be in the future") LocalDate publishedDate,

        @NotEmpty(message = "The book must have at least one author") Set<AuthorDto> authors) {
}
