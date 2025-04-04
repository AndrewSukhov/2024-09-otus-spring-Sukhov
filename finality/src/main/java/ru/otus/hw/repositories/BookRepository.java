package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book-author-genre")
    Optional<Book> findById(Long id);

    @EntityGraph(value = "book-author-genre")
    List<Book> findAll();

    @Query("SELECT COUNT(b) FROM Book b")
    long countAllBooks();
}