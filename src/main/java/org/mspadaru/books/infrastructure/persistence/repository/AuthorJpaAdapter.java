package org.mspadaru.books.infrastructure.persistence.repository;

import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.port.out.AuthorRepository;
import org.mspadaru.books.infrastructure.persistence.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Hexagonal adapter that connects the domain-level AuthorRepository
 * to a JPA-backed persistence layer.
 *
 * Converts between domain models and JPA entities.
 */
@Repository
public class AuthorJpaAdapter implements AuthorRepository {

    private final AuthorJpaRepository jpaRepository;

    public AuthorJpaAdapter(AuthorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Set<Author> findAll() {
        return jpaRepository.findAll().stream().map(AuthorMapper::toDomain).collect(Collectors.toSet());
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return jpaRepository.findById(id).map(AuthorMapper::toDomain);
    }

    @Override
    public Author create(Author author) {
        return AuthorMapper.toDomain(jpaRepository.save(AuthorMapper.toEntity(author)));
    }

    @Override
    public Author update(Author author) {
        return AuthorMapper.toDomain(jpaRepository.save(AuthorMapper.toEntity(author)));
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}
