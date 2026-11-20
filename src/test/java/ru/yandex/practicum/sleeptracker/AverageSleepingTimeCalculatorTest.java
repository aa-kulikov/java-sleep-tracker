package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageSleepingTimeCalculatorTest {

    @Test
    void calculateAverageSleepingDuration() {
        AverageSleepingTimeCalculator calculator = new AverageSleepingTimeCalculator();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T07:00"), SleepQuality.GOOD),   // 480 мин.
                new SleepingSession(LocalDateTime.parse("2025-10-02T14:00"),
                        LocalDateTime.parse("2025-10-02T15:00"), SleepQuality.NORMAL), // 60 мин.
                new SleepingSession(LocalDateTime.parse("2025-10-02T23:00"),
                        LocalDateTime.parse("2025-10-03T06:00"), SleepQuality.GOOD)    // 420 мин.
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        //Средняя продолжительность сна всех сессий равна 320 мин
        assertEquals(320.0, result.getResult());
    }

    @Test
    void emptySessionsList() {
        AverageSleepingTimeCalculator calculator = new AverageSleepingTimeCalculator();
        SleepAnalysisResult result = calculator.apply(List.of());

        //Список сессий пуст — средняя продолжительность 0
        assertEquals(0.0, result.getResult());
    }
}