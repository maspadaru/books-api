package org.mspadaru.books.domain.model;

import org.mspadaru.books.domain.model.constraints.AuthorConstraints;

import java.util.UUID;

public record Author(UUID id, String name) {
    public Author {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Author name is required");
        }
        if (name.length() > AuthorConstraints.NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(AuthorConstraints.NAME_SIZE_MESSAGE);
        }
    }
}
