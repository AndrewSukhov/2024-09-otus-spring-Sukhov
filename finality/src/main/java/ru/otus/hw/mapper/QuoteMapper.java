package ru.otus.hw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.hw.dto.QuoteDto;
import ru.otus.hw.models.Quote;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuoteMapper {
    @Mapping(source = "book.id", target = "bookId")
    QuoteDto toQuoteDto(Quote quote);

    @Mapping(source = "book.id", target = "bookId")
    List<QuoteDto> toQuoteDtos(List<Quote> quotes);

}