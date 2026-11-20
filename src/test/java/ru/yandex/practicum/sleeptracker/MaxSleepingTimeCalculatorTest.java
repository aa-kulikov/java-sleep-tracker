package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxSleepingTimeCalculatorTest {

    @Test
    void findMaxSleepingDuration() {
        MaxSleepingTimeCalculator calculator = new MaxSleepingTimeCalculator();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T07:00"), SleepQuality.GOOD),   // 480 мин.
                new SleepingSession(LocalDateTime.parse("2025-10-02T14:00"),
                        LocalDateTime.parse("2025-10-02T14:45"), SleepQuality.NORMAL), // 45 мин.
                new SleepingSession(LocalDateTime.parse("2025-10-02T22:00"),
                        LocalDateTime.parse("2025-10-03T08:30"), SleepQuality.GOOD)    // 630 мин.
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        //Макс. продолжительность сна среди всех сессий равна 630 минутам
        assertEquals(630L, result.getResult());
    }

    @Test
    void emptySessionsList() {
        MaxSleepingTimeCalculator calculator = new MaxSleepingTimeCalculator();
        SleepAnalysisResult result = calculator.apply(List.of());

        //Список сессий пуст — макс. продолжительность равна 0
        assertEquals(0L, result.getResult());
    }
}