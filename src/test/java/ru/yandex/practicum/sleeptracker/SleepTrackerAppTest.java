package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepTrackerAppTest {

    @Test
    void analyzeSleepLogFile() throws IOException {
        Path logFile = Files.createTempFile("test_sleep_log", ".txt");

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile.toFile(), StandardCharsets.UTF_8,
                false), true)) {
            writer.println("01.10.25 23:30;02.10.25 09:30;GOOD");
            writer.println("02.10.25 14:00;02.10.25 15:00;BAD");

            List<SleepAnalysisResult> results = SleepTrackerApp.analyzeFile( logFile.toString(), new SleepAnalyzer(),
                    new Logger());

            //Вполнились все 7 функций анализа
            assertEquals(7, results.size());

            //Общее количество сессий сна в файле - 2
            assertEquals(2L, results.get(0).getResult());

            //Минимальная продолжительность сна
            assertEquals(60L, results.get(1).getResult());

            //Максимальная продолжительность сна
            assertEquals(600L, results.get(2).getResult());

            //Средняя продолжительность сна
            assertEquals(330.0, results.get(3).getResult());

            //Количество сессий с плохим качеством сна - 1
            assertEquals(1L, results.get(4).getResult());

            //Количество бессонных ночей за период - 0
            assertEquals(0L, results.get(5).getResult());

            //Хронотип - сова
            assertEquals(Chronotype.OWL, results.get(6).getResult());

        } finally {
            Files.deleteIfExists(logFile);
        }
    }
}

