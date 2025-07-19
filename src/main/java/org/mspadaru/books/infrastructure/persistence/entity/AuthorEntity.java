package org.mspadaru.books.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;
import org.mspadaru.books.domain.model.constraints.AuthorConstraints;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


/**
 * JPA entity representing an author.
 * <p>
 * This entity maps to the "author" table in the database.
 * Each author has a unique UUID, a name, and a many-to-many relationship with books.
 * The relationship is owned by the {@link BookEntity} side and is mapped via the "book_author" join table.
 */
@Entity
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Size(max = AuthorConstraints.NAME_MAX_LENGTH)
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authors")
    private final Set<BookEntity> books = new HashSet<>();

    public AuthorEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        AuthorEntity that = (AuthorEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AuthorEntity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
