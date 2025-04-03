package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.TagDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.TagMapper;
import ru.otus.hw.models.Tag;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.TagRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final BookRepository bookRepository;

    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<TagDto> findById(long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        return optionalTag.map(tagMapper::toTagDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> findByBookId(long bookId) {
        List<Tag> tags = tagRepository.findAllByBookId(bookId);
        return tagMapper.toTagDtos(tags);
    }

    @Override
    @Transactional
    public TagDto insert(long bookId, String text) {
        var book = bookRepository.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book with id='%s' not found".formatted(bookId)));

        Tag tag = Tag.builder()
            .text(text)
            .book(book)
            .createdAt(LocalDate.now())
            .build();

        tag = tagRepository.save(tag);
        return tagMapper.toTagDto(tag);
    }

    @Override
    @Transactional
    public TagDto update(long id, String text) {
        var tag = tagRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tag with id='%s' not found".formatted(id)));
        tag.setText(text);
        return tagMapper.toTagDto(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        tagRepository.deleteById(id);
    }
}