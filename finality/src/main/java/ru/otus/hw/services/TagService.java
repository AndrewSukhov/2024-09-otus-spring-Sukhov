package ru.otus.hw.services;

import ru.otus.hw.dto.TagDto;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Optional<TagDto> findById(long id);

    List<TagDto> findByBookId(long bookId);

    TagDto insert(long bookId, String text);

    TagDto update(long id, String text);

    void deleteById(long id);
}