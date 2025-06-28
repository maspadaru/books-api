package org.mspadaru.books.application.service;

import org.mspadaru.books.application.port.in.AuthorService;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.domain.port.out.AuthorRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class DefaultAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;

    public DefaultAuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Set<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findAuthorById(UUID id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.create(author);
    }

    @Override
    public Optional<Author> updateAuthor(UUID id, Author author) {
        return authorRepository.update(id, author);
    }

    @Override
    public boolean deleteAuthor(UUID id) {
        return authorRepository.delete(id);
    }

}
