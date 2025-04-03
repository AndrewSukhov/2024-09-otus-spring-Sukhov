package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByBookId(Long bookId);

    void deleteAllByBookId(Long id);

//    List<Tag> findByTextContaining(String text);
}