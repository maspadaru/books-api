package org.mspadaru.books.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.mspadaru.books.domain.model.constraints.AuthorConstraints;

public record AuthorRequest(
        @Size(max = AuthorConstraints.NAME_MAX_LENGTH, message = AuthorConstraints.NAME_SIZE_MESSAGE) @NotBlank(message = "Name must not be blank") String name) {
}
