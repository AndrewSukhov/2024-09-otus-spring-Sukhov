package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        List<Question> questionsAndAnswers =  questionDao.findAll();

        for (Question questionAndAnswer : questionsAndAnswers) {
            printQuestionAndAnswers(questionAndAnswer);
        }
    }

    private void printQuestionAndAnswers(Question question) {
        ioService.printLine("");
        ioService.printFormattedLine("%s", question.text()) ;

        for (int i = 0; i < question.answers().size(); i++) {
            ioService.printFormattedLine("%s). %s", i + 1, question.answers().get(i).text());
        }
    }
}
