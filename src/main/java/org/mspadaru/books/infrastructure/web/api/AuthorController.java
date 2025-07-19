package org.mspadaru.books.infrastructure.web.api;

import jakarta.validation.Valid;
import org.mspadaru.books.application.port.in.AuthorService;
import org.mspadaru.books.domain.model.Author;
import org.mspadaru.books.infrastructure.web.dto.AuthorDto;
import org.mspadaru.books.infrastructure.web.dto.AuthorRequest;
import org.mspadaru.books.infrastructure.web.mapper.AuthorDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Set<AuthorDto>> getAllAuthors() {
        Set<AuthorDto> authors = authorService.findAllAuthors().stream().map(AuthorDtoMapper::toDto).collect(
                Collectors.toSet());
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable UUID id) {
        return authorService.findAuthorById(id).map(AuthorDtoMapper::toDto).map(ResponseEntity::ok).orElse(
                ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorRequest request) {
        Author author = new Author(null, request.name());
        Author saved = authorService.createAuthor(author);
        URI location = URI.create("/api/authors/" + saved.id());
        return ResponseEntity.created(location).body(AuthorDtoMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable UUID id, @Valid @RequestBody AuthorRequest request) {
        Author authorToUpdate = new Author(id, request.name());
        return authorService.updateAuthor(id, authorToUpdate).map(AuthorDtoMapper::toDto).map(
                ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable UUID id) {
        if (!authorService.deleteAuthor(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
