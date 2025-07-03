package org.mspadaru.books.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record BookRequest(

        @NotBlank(message = "Title must not be blank") String title,

        @Size(min = 10, max = 17, message = "ISBN must be between 10 and 17 characters") String isbn,

        @PastOrPresent(message = "Published date must not be in the future") LocalDate publishedDate,

        @NotEmpty(message = "The book must have at least one author") Set<AuthorDto> authors) {
}
