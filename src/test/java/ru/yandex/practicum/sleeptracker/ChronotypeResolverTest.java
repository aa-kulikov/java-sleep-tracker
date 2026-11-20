package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeResolverTest {

    @Test
    void resolveOwlAndIgnoreDaytimeSleep() {
        ChronotypeResolver resolver = new ChronotypeResolver();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:30"),
                        LocalDateTime.parse("2025-10-02T09:30"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-03T00:15"),
                        LocalDateTime.parse("2025-10-03T10:00"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-03T21:30"),
                        LocalDateTime.parse("2025-10-04T06:30"), SleepQuality.GOOD),
                //Дневные сессии должны игнорироваться при подсчёте хронотипа:
                new SleepingSession(LocalDateTime.parse("2025-10-04T13:00"),
                        LocalDateTime.parse("2025-10-04T14:00"), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.parse("2025-10-05T14:00"),
                        LocalDateTime.parse("2025-10-05T15:00"), SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = resolver.apply(sessions);

        //Ожидается хронотип "Сова"
        assertEquals(Chronotype.OWL, result.getResult());
    }

    @Test
    void resolveSkylark() {
        ChronotypeResolver resolver = new ChronotypeResolver();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T21:00"),
                        LocalDateTime.parse("2025-10-02T06:00"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-02T21:45"),
                        LocalDateTime.parse("2025-10-03T06:50"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-03T22:30"),
                        LocalDateTime.parse("2025-10-04T07:30"), SleepQuality.GOOD)
        );

        SleepAnalysisResult result = resolver.apply(sessions);

        //Ожидается хронотип "Жаворонок"
        assertEquals(Chronotype.SKYLARK, result.getResult());
    }

    @Test
    void resolvePigeonWhenTieOccurs() {
        ChronotypeResolver resolver = new ChronotypeResolver();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-01T23:30"),
                        LocalDateTime.parse("2025-10-02T09:30"), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.parse("2025-10-02T21:30"),
                        LocalDateTime.parse("2025-10-03T06:30"), SleepQuality.GOOD)
        );

        SleepAnalysisResult result = resolver.apply(sessions);

        //При равном количестве ночей - "Голубь"
        assertEquals(Chronotype.PIGEON, result.getResult());
    }

    @Test
    void resolvePigeonForMidnightSleeperWithEarlyWakeUp() {
        ChronotypeResolver resolver = new ChronotypeResolver();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.parse("2025-10-02T00:30"),
                        LocalDateTime.parse("2025-10-02T06:30"), SleepQuality.GOOD));

        SleepAnalysisResult result = resolver.apply(sessions);

        //Начало сессии после полуночи и подъём в 06:30 - "Голубь"
        assertEquals(Chronotype.PIGEON, result.getResult());
    }
}