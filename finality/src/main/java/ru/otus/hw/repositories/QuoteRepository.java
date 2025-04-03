package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Quote;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findAllByBookId(Long bookId);

    void deleteAllByBookId(Long id);

//    @Query("SELECT q.book FROM Quote q WHERE q.text LIKE %:text%")
//    List<Book> getBooksByQuoteText(@Param("text") String text);

}