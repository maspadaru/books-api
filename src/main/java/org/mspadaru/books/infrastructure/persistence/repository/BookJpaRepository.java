package org.mspadaru.books.infrastructure.persistence.repository;

import org.mspadaru.books.infrastructure.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface BookJpaRepository extends JpaRepository<BookEntity, UUID> {

    Set<BookEntity> findByAuthors_Id(@Param("authorId") UUID authorId);

}
