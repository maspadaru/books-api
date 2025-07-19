package org.mspadaru.books.domain.model.constraints;

public final class AuthorConstraints {

    public static final int NAME_MAX_LENGTH = 100;
    public static final String NAME_SIZE_MESSAGE = "Title must not exceed " + NAME_MAX_LENGTH + " characters";
    ;

    private AuthorConstraints() {
        // Prevent instantiation
    }

}
