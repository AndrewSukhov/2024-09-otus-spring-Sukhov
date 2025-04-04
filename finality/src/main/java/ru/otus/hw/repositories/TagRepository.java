package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Quote;
import ru.otus.hw.models.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByBookId(Long bookId);

    void deleteAllByBookId(Long id);

    @Query("SELECT t FROM Tag t WHERE LOWER(t.text) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<Tag> getByTextContainingIgnoreCase(@Param("text") String text);

}