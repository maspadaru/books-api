package org.mspadaru.books.domain.model.constraints;

public final class BookConstraints {

    public static final int TITLE_MAX_LENGTH = 255;
    public static final int ISBN_MAX_LENGTH = 20;
    public static final String TITLE_SIZE_MESSAGE = "Title must not exceed " + TITLE_MAX_LENGTH + " characters";
    public static final String ISBN_SIZE_MESSAGE = "ISBN must be at most " + ISBN_MAX_LENGTH + " characters";

    private BookConstraints() {
        // Prevent instantiation
    }
}
