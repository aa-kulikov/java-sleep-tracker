package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinSleepingTimeCalculatorTest {

    @Test
    void findMinSleepingDuration() {
        MinSleepingTimeCalculator calculator = new MinSleepingTimeCalculator();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T07:00"), SleepQuality.GOOD),   // 480 мин.
                new SleepingSession(LocalDateTime.parse("2025-10-02T14:00"),
                        LocalDateTime.parse("2025-10-02T14:45"), SleepQuality.NORMAL), // 45 мин.
                new SleepingSession(LocalDateTime.parse("2025-10-02T23:00"),
                        LocalDateTime.parse("2025-10-03T05:00"), SleepQuality.BAD)     // 360 мин.
        );

        SleepAnalysisResult result = calculator.apply(sessions);

        //Мин. продолжительность сна среди всех сессий равна 45 минутам
        assertEquals(45L, result.getResult());
    }

    @Test
    void emptySessionsList() {
        MinSleepingTimeCalculator calculator = new MinSleepingTimeCalculator();
        SleepAnalysisResult result = calculator.apply(List.of());

        //Список сессий пуст — мин. продолжительность равна 0
        assertEquals(0L, result.getResult());
    }
}