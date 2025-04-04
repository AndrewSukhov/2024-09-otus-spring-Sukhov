package ru.otus.hw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.hw.dto.TagDto;
import ru.otus.hw.models.Tag;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(source = "book.id", target = "bookId")
    TagDto toTagDto(Tag tag);

    @Mapping(source = "book.id", target = "bookId")
    List<TagDto> toTagDtos(List<Tag> tags);
}