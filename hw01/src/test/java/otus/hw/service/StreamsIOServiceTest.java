package otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.service.StreamsIOService;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StreamsIOServiceTest
{
    @InjectMocks
    private StreamsIOService streamsIOService;

    @Mock
    private PrintStream printStream;

    @Test
    @DisplayName("should printLine work")
    void printLine() {
        // given
        String test = "test";

        // when
        streamsIOService.printLine(test);

        // then
        verify(printStream).println(test);
    }

    @Test
    @DisplayName("should printFormattedLine work")
    void printFormattedLine() {
        // given
        String test = "test";
        String result = test + "%n";

        // when
        streamsIOService.printFormattedLine(test);

        // then
        verify(printStream).printf(result);
    }
}