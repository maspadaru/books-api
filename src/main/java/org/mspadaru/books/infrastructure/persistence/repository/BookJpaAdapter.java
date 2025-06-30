package org.mspadaru.books.infrastructure.persistence.repository;

import org.mspadaru.books.domain.model.Book;
import org.mspadaru.books.domain.port.out.BookRepository;
import org.mspadaru.books.infrastructure.persistence.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class BookJpaAdapter implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    public BookJpaAdapter(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public Set<Book> findAll() {
        return bookJpaRepository.findAll().stream().map(BookMapper::toDomain).collect(Collectors.toSet());
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return bookJpaRepository.findById(id).map(BookMapper::toDomain);
    }

    @Override
    public Book create(Book book) {
        return BookMapper.toDomain(bookJpaRepository.save(BookMapper.toEntity(book)));
    }

    @Override
    public Book update(Book book) {
        return BookMapper.toDomain(bookJpaRepository.save(BookMapper.toEntity(book)));
    }

    @Override
    public void delete(UUID id) {
        bookJpaRepository.deleteById(id);
    }

    @Override
    public Set<Book> findAllByAuthorId(UUID authorId) {
        return bookJpaRepository.findByAuthors_Id(authorId).stream().map(BookMapper::toDomain).collect(
                Collectors.toSet());
    }

}
