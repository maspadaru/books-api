# Book Inventory – Hexagonal Architecture with Spring Boot

This is a Book Inventory management system built with **Spring Boot**, structured using **Hexagonal Architecture (Ports & Adapters)**. The project models a real-world scenario with books and authors, where:

- 📚 A book can have **multiple authors**
- ✍️ An author can write **multiple books**

## Features

- ✅ Create, update, and delete books
- ✅ Create and list authors
- 🔗 Assign and retrieve relationships between books and authors
- 🧱 Built using Hexagonal Architecture
- 🗄️ Uses **Spring Data JPA** with a **many-to-many relationship**
- 🧪 Uses in-memory **H2 database** for quick development and testing

## Technologies

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Gradle

## Project Structure

```

com.example.inventory
├── domain             # Core business logic and models (Book, Author, Ports)
├── application        # Use cases (e.g. register book, link authors)
├── infrastructure     # Adapters: controllers, JPA repositories, mappers
│   ├── persistence
│   └── web
└── InventoryApplication.java  # Spring Boot entry point

````

## Domain Model

- `Book`:
  - `id`, `title`, `isbn`, `publishedDate`
  - `Set<Author> authors`

- `Author`:
  - `id`, `name`
  - `Set<Book> books`

## API Endpoints

### Books

| Method | Endpoint            | Description                |
|--------|---------------------|----------------------------|
| GET    | `/books`            | List all books             |
| GET    | `/books/{id}`       | Get book by ID             |
| POST   | `/books`            | Create a new book          |
| PUT    | `/books/{id}`       | Update a book              |
| DELETE | `/books/{id}`       | Delete a book              |

### Authors

| Method | Endpoint             | Description                |
|--------|----------------------|----------------------------|
| GET    | `/authors`           | List all authors           |
| GET    | `/authors/{id}`      | Get author by ID           |
| POST   | `/authors`           | Create a new author        |
| PUT    | `/authors/{id}`      | Update an author           |
| DELETE | `/authors/{id}`      | Delete an author           |

### Relationships

| Method | Endpoint                                        | Description                            |
|--------|-------------------------------------------------|----------------------------------------|
| POST   | `/books/{bookId}/authors/{authorId}`            | Assign author to book                  |
| DELETE | `/books/{bookId}/authors/{authorId}`            | Remove author from book                |

## Running the App

```bash
./gradlew bootRun
````

Or run `InventoryApplication` directly in your IDE.

## Notes

* The **hexagonal architecture** ensures a clean separation between domain logic and technical concerns like HTTP and database access.
* JPA handles the many-to-many relationship via a join table.
* You can switch to PostgreSQL or another database by changing a few lines in `application.properties`.

## License

MIT – use, share, and modify freely.
