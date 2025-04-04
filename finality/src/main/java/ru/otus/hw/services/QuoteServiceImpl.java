package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.QuoteDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.QuoteMapper;
import ru.otus.hw.models.Quote;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.QuoteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;

    private final BookRepository bookRepository;

    private final QuoteMapper quoteMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<QuoteDto> findById(long id) {
        Optional<Quote> optionalQuote = quoteRepository.findById(id);
        return optionalQuote.map(quoteMapper::toQuoteDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteDto> findByBookId(long bookId) {
        List<Quote> quotes = quoteRepository.findAllByBookId(bookId);
        return quoteMapper.toQuoteDtos(quotes);
    }

    @Override
    @Transactional
    public QuoteDto insert(long bookId, String text) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id='%s' not found".formatted(bookId)));

        Quote quote = Quote.builder()
                .text(text)
                .book(book)
                .createdAt(LocalDate.now())
                .build();

        quote = quoteRepository.save(quote);
        return quoteMapper.toQuoteDto(quote);
    }

    @Override
    @Transactional
    public QuoteDto update(long id, String text) {
        var quote = quoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quote with id='%s' not found".formatted(id)));
        quote.setText(text);
        return quoteMapper.toQuoteDto(quoteRepository.save(quote));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        quoteRepository.deleteById(id);
    }

    @Override
    public List<QuoteDto> findByTextContaining(String text) {
        List<Quote> quotes = quoteRepository.getByTextContainingIgnoreCase(text);
        return quoteMapper.toQuoteDtos(quotes);
    }

}