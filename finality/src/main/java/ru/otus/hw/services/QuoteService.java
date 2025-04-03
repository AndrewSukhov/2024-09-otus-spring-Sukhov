package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.QuoteDto;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface QuoteService
{
    Optional<QuoteDto> findById(long id);

    List<QuoteDto> findByBookId(long bookId);

    QuoteDto insert(long bookId, String text);

    QuoteDto update(long id, String text);

    void deleteById(long id);

//    List<Book> findBooksByQuoteText(String text);
}