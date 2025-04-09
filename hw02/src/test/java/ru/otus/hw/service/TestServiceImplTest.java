package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestServiceImplTest
{

    private TestServiceImpl testService;
    private QuestionDao questionDao;
    private IOService ioService;

    @BeforeEach
    public void setUp()
    {
        questionDao = mock(QuestionDao.class);
        ioService = mock(IOService.class);
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    public void testExecuteTestFor()
    {
        //given
        Student student = new Student("Vasya", "Pupkin");
        List<Question> questions = List.of(
            new Question("Amount 4+3 is?", List.of(
                new Answer("7", true),
                new Answer("107", false),
                new Answer("1107", false)
            ))
        );

        //when
        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString())).thenReturn(1);

        TestResult result = testService.executeTestFor(student);

        //then
        assertEquals(1, result.getRightAnswersCount());
        assertEquals(1, result.getAnsweredQuestions().size());
        assertEquals("Amount 4+3 is?", result.getAnsweredQuestions().get(0).text());

        verify(ioService).printLine("");
        verify(ioService).printFormattedLine("Please answer the questions below%n");
        verify(ioService).printLine("Amount 4+3 is?");
        verify(ioService).printFormattedLine("%d). %s", 1, "7");
        verify(ioService).printFormattedLine("%d). %s", 2, "107");
        verify(ioService).printFormattedLine("%d). %s", 3, "1107");
        verify(ioService).readIntForRangeWithPrompt(1, 3, "Enter the answer: ", "Incorrect number. You must enter a number between 1 and 3");
    }
}