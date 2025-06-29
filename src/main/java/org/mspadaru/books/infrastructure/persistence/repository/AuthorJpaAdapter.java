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

@Repository
public class AuthorJpaAdapter implements AuthorRepository {

    @Autowired
    private final AuthorJpaRepository jpaRepository;

    public AuthorJpaAdapter(AuthorJpaRepository jpaRepository, AuthorMapper authorMapper) {
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
    public Optional<Author> update(UUID id, Author author) {
        {
            return Optional.of(jpaRepository.save(AuthorMapper.toEntity(author))).map(AuthorMapper::toDomain);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(UUID id) {
        if (findById(id).isPresent()) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
