package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaxSleepingTimeCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Максимальная продолжительность сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, 0L);
        }

        long maxDuration = sessions.stream()
                .map(SleepingSession::getDuration)
                .map(Duration::toMinutes)
                .max(Long::compareTo)
                .orElseThrow();

        return new SleepAnalysisResult(DESCRIPTION, maxDuration);
    }
}