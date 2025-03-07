package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        var questions = questionDao.findAll();

        // Задать вопрос, получить ответ
        return processQuestion(questions, student);
    }

    private TestResult processQuestion(List<Question> questions, Student student) {
        TestResult testResult = new TestResult(student);

        for (Question question : questions) {
            ioService.printLine(question.text());
            List<Answer> answers = question.answers();

            printAnswerVariants(answers);

            boolean isAnswerValid = isCorrectAnswer(question, answers);

            testResult.applyAnswer(question, isAnswerValid);
        }

        return testResult;
    }

    private boolean isCorrectAnswer(Question question, List<Answer> answers) {
        if (answers.isEmpty()) {
            return false;
        }
        int size = question.answers().size();
        String errorMessage = ioService.getMessage("TestService.error.message.Incorrect.number") + size;
        String prompt = ioService.getMessage("TestService.enter.the.answer.number");
        int answerIndex = ioService.readIntForRangeWithPrompt(1, size, prompt, errorMessage);

        return answers.get(answerIndex - 1).isCorrect();
    }

    private void printAnswerVariants(List<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            ioService.printFormattedLine("%d). %s", i + 1, answers.get(i).text());
        }
    }

}
