package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSleepingTimeCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Средняя продолжительность сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, 0.0);
        }

        double averageDuration = sessions.stream()
                .map(SleepingSession::getDuration)
                .mapToLong(Duration::toMinutes)
                .average()
                .orElseThrow();

        return new SleepAnalysisResult(DESCRIPTION, averageDuration);
    }
}