# Blog — Simple Spring Boot Blogging Platform (Thymeleaf, In-Memory Repositories)

This is a small educational project — a blogging platform built with Spring Boot and Thymeleaf.
Data is stored using simple in-memory repositories (HashMap), making it perfect for learning, demos, or as a base for future development.

---

## Features

* Posts list page with pagination
* View post by `id` or `slug`
* Create / edit / delete posts (with validation form)
* Users list and user registration
* Thymeleaf templates (English texts)
* Simple in-memory data storage (custom repositories)

---

## Tech Stack

* Java 21
* Spring Boot 3.3.x

  * Web
  * Thymeleaf
  * Validation
* Maven
* Custom in-memory repositories (no database)

---

## Requirements

* Java 21
* Maven 3.x
* Default port: `8080`

---

## Quick Start

1. Clone the repo:

```bash
git clone <your-repo-url>
cd <repo-folder>
```

2. Build & run:

```bash
# build
mvn clean package

# run with plugin
mvn spring-boot:run

# or run built jar
java -jar target/Blog-1.0-SNAPSHOT.jar
```

3. Open in browser:

```
http://localhost:8080/posts
```

---

## Main Endpoints

### **Posts**

* `GET /posts` — list with pagination
* `GET /posts/{id|slug}` — view post by id or slug
* `GET /posts/new` — create post page
* `POST /posts/save` — save new/edited post
* `GET /posts/edit/{id}` — edit post page
* `POST /posts/delete/{id}` — delete post

### **Users**

* `GET /users` — users list
* `GET /users/register` — registration form
* `POST /users/register` — register new user
* `POST /users/delete/{id}` — delete user

---

## Project Structure

```
src/
  main/
    java/com/valkoluv/blog/
      controller/      # PostController, UserController
      dto/             # PostDto, UserDto
      model/           # Post, User
      repository/      # In-memory repositories
      service/         # Business logic
      BlogApplication.java
    resources/
      templates/       # Thymeleaf templates
pom.xml
```

---

## Notes About Data

* All data is stored in memory (`HashMap`).
* Data resets every time the app restarts.
* Simple demo data is auto-loaded.
* Validation is implemented with `jakarta.validation`.
* Slug generation is automatic (lowercase + hyphens).

  * Note: It’s a very basic implementation.

---

## Future Improvements

You can extend this project by adding:

* **Database support** (PostgreSQL / H2 + JPA)
* **Authentication/authorization** (Spring Security)
* **Image uploads**
* **Tags, categories, search**
* **Improved slug generation**
* **REST API version of the blog**

---

## Example Usage (curl)

### Register a user

```bash
curl -X POST http://localhost:8080/users/register \
  -d "username=testuser" -d "email=test@example.com" -d "password=secret123"
```

### Create a post

```bash
curl -X POST http://localhost:8080/posts/save \
  -d "title=My First Post" \
  -d "content=Some long content here..." \
  -d "authorId=1"
```

---

## Contributions

Pull requests and issues are welcome.
If you want, I can also help you:

* add JPA + H2 migration
* integrate Spring Security
* improve the Thymeleaf templates
* generate REST API documentation

---
