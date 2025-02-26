package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());
            List<QuestionDto> questions = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(is))
                .withSkipLines(1)
                .withSeparator(';')
                .withType(QuestionDto.class)
                .build()
                .parse();
            return questions.stream().map(QuestionDto::toDomainObject).toList();
        } catch (Exception e) {
            throw new QuestionReadException("Error reading file with questions", e);
        }
    }
}
