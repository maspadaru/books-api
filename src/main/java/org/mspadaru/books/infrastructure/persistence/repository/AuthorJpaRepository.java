package org.mspadaru.books.infrastructure.persistence.repository;

import org.mspadaru.books.infrastructure.persistence.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorJpaRepository extends JpaRepository<AuthorEntity, UUID> {
}
