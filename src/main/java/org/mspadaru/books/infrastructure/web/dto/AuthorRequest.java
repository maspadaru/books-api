package org.mspadaru.books.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequest(
        @NotBlank(message = "Name must not be blank")
        String name
) {}
