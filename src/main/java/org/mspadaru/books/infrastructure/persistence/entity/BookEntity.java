package org.mspadaru.books.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;
import org.mspadaru.books.domain.model.constraints.BookConstraints;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * JPA entity representing a book.
 * <p>
 * This entity maps to the "book" table in the database.
 * Each book has a UUID, title, optional ISBN, publication date, and a many-to-many relationship with authors.
 * The authors are stored via the "book_author" join table, and this entity owns the relationship.
 */
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Size(max = BookConstraints.TITLE_MAX_LENGTH)
    @Column(nullable = false)
    private String title;

    @Size(max = BookConstraints.ISBN_MAX_LENGTH)
    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<AuthorEntity> authors = new HashSet<>();

    public BookEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        BookEntity that = (BookEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(isbn,
                that.isbn) && Objects.equals(publishedDate, that.publishedDate) && Objects.equals(authors,
                that.authors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(isbn);
        result = 31 * result + Objects.hashCode(publishedDate);
        result = 31 * result + Objects.hashCode(authors);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BookEntity{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", isbn='").append(isbn).append('\'');
        sb.append(", publishedDate=").append(publishedDate);
        sb.append(", authors=").append(authors);
        sb.append('}');
        return sb.toString();
    }

}
