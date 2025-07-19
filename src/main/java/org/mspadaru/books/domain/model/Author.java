package org.mspadaru.books.domain.model;

import java.util.UUID;

public record Author(UUID id, String name) {
    public Author {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Author name is required");
    }
}
