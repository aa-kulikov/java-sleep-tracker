package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadQualitySessionsCounterTest {

    @Test
    void countBadQualitySessions() {
        BadQualitySessionsCounter counter = new BadQualitySessionsCounter();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T07:00"), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.parse("2025-10-02T23:00"),
                        LocalDateTime.parse("2025-10-03T07:00"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-03T23:00"),
                        LocalDateTime.parse("2025-10-04T07:00"), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.parse("2025-10-04T23:00"),
                        LocalDateTime.parse("2025-10-05T07:00"), SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = counter.apply(sessions);

        //Ко-во сессий с плохим качеством сна - 2
        assertEquals(2L, result.getResult());
    }

    @Test
    void noBadQualitySessions() {
        BadQualitySessionsCounter counter = new BadQualitySessionsCounter();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:00"),
                        LocalDateTime.parse("2025-10-02T07:00"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-02T23:00"),
                        LocalDateTime.parse("2025-10-03T07:00"), SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = counter.apply(sessions);

        //Сессии с плохим качеством сна отсутствуют
        assertEquals(0L, result.getResult());
    }
}