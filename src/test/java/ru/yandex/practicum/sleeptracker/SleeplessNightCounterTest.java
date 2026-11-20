package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightCounterTest {

    @Test
    void countNightWithDaytimeSleep() {
        SleeplessNightCounter counter = new SleeplessNightCounter();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T03:00"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-03T07:00"),
                        LocalDateTime.parse("2025-10-03T11:00"), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.parse("2025-10-03T23:30"),
                        LocalDateTime.parse("2025-10-04T07:00"), SleepQuality.GOOD));

        SleepAnalysisResult result = counter.apply(sessions);

        //Ночь с 02.10 по 03.10 считается как бессонная (сессия сна 07:00–11:00 не пересекает 00:00–06:00)
        assertEquals(1L, result.getResult());
    }

    @Test
    void sleepAfterMidnightBeforeSix() {
        SleeplessNightCounter counter = new SleeplessNightCounter();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T06:30"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-03T02:00"),
                        LocalDateTime.parse("2025-10-03T05:30"), SleepQuality.NORMAL));

        SleepAnalysisResult result = counter.apply(sessions);

        //Ночь не бессонная, т.к. в интервал 00:00–06:00 балы сессия сна 02:00–05:30
        assertEquals(0L, result.getResult());
    }

    @Test
    void firstSessionBeforeNoon() {
        SleeplessNightCounter counter = new SleeplessNightCounter();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T08:00"),
                        LocalDateTime.parse("2025-10-01T10:00"), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T07:00"), SleepQuality.GOOD));

        SleepAnalysisResult result = counter.apply(sessions);

        //Первая сессия за день до 12:00, значит ночь за эту дату бессонная
        assertEquals(1L, result.getResult());
    }

    @Test
    void sleeplessNightsAcrossMonths() {
        SleeplessNightCounter counter = new SleeplessNightCounter();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-30T23:30"),
                        LocalDateTime.parse("2025-10-31T07:00"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-11-02T23:30"),
                        LocalDateTime.parse("2025-11-03T07:00"), SleepQuality.GOOD));

        SleepAnalysisResult result = counter.apply(sessions);

        //Переход в след.месяц с учетом бессоных ночей
        assertEquals(2L, result.getResult());
    }

    @Test
    void emptySessionsList() {
        SleeplessNightCounter counter = new SleeplessNightCounter();
        SleepAnalysisResult result = counter.apply(List.of());

        //Список ссесий пуст - бессонных ночей 0
        assertEquals(0L, result.getResult());
    }
}