# Book Inventory – Hexagonal Architecture with Spring Boot

This is a Book Inventory management system built with **Spring Boot**, structured using **Hexagonal Architecture (Ports & Adapters)**. The project models a real-world scenario with books and authors, where:

- 📚 A book can have **multiple authors**
- ✍️ An author can write **multiple books**

## Features

- ✅ Create, update, delete, and retrieve books and authors
- 🔗 Many-to-many relationship between books and authors
- ✅ Validation enforced at API, domain, and persistence levels
- 🧪 Robust unit and integration tests
- 💡 Clean separation using DTOs and mappers
- 🧱 Structured with Hexagonal Architecture (Ports & Adapters)
- 🗄️ Uses **Spring Data JPA** and in-memory **H2 database** for development


## Technologies

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Gradle

## Project Structure

```

org.mspadaru.books
├── domain             # Core business logic and models (Book, Author, Ports)
├── application        # Use cases (e.g. register book, link authors)
├── infrastructure     # Adapters: controllers, JPA repositories, mappers
│   ├── persistence
│   └── web
└── BooksApplication.java  # Spring Boot entry point

````

## Domain Model

- `Book`:
  - `id`, `title`, `isbn`, `publishedDate`
  - `Set<Author> authors`

- `Author`:
  - `id`, `name`
  - `Set<Book> books`

## API Endpoints

Base path: `/api`

### Books

| Method | Endpoint                   | Description       |
|--------|----------------------------|-------------------|
| GET    | `/api/books`              | List all books    |
| GET    | `/api/books/{id}`         | Get book by ID    |
| POST   | `/api/books`              | Create a book     |
| PUT    | `/api/books/{id}`         | Update a book     |
| DELETE | `/api/books/{id}`         | Delete a book     |

### Authors

| Method | Endpoint                   | Description        |
|--------|----------------------------|--------------------|
| GET    | `/api/authors`           | List all authors   |
| GET    | `/api/authors/{id}`      | Get author by ID   |
| POST   | `/api/authors`           | Create an author   |
| PUT    | `/api/authors/{id}`      | Update an author   |
| DELETE | `/api/authors/{id}`      | Delete an author   |

### Extra

| Method | Endpoint                                | Description                  |
|--------|------------------------------------------|------------------------------|
| GET    | `/api/books/by-author/{authorId}`       | List books by author ID      |

## Running the App

```bash
./gradlew bootRun
````

Or run `BookApplication` directly in your IDE.

## Testing

This project includes:

- ✅ **Unit tests** for domain logic and controllers
- ✅ **Integration tests** for persistence adapters
- ✅ **Web layer tests** with `MockMvc`

You can run all tests with:

```bash
./gradlew test
````


## Notes

* The **hexagonal architecture** ensures a clean separation between domain logic and technical concerns like HTTP and database access.
* JPA handles the many-to-many relationship via a join table.
* You can switch to PostgreSQL or another database by changing a few lines in `application.properties`.

## License

MIT – use, share, and modify freely.
