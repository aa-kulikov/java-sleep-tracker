package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepingSessionsCounterTest {

    @Test
    void countTotalSessions() {
        SleepingSessionsCounter counter = new SleepingSessionsCounter();
        List<SleepingSession> sessions = new ArrayList<>();
        for (int day = 1; day <= 5; day++) {
            sessions.add(new SleepingSession(
                    LocalDateTime.parse(String.format("2025-10-%02dT23:00", day)),
                    LocalDateTime.parse(String.format("2025-10-%02dT07:00", day + 1)),
                    SleepQuality.GOOD));
        }

        SleepAnalysisResult result = counter.apply(sessions);

        //Общее количество сессий сна в списке — 5
        assertEquals(5L, result.getResult());

        //Описание функции соответствует ожидаемому
        assertEquals("Общее количество сессий сна", result.getFunctionDescription());
    }

    @Test
    void emptySessionsList() {
        SleepingSessionsCounter counter = new SleepingSessionsCounter();
        SleepAnalysisResult result = counter.apply(List.of());

        //Список сессий пуст — количество сессий равно 0
        assertEquals(0L, result.getResult());
    }
}