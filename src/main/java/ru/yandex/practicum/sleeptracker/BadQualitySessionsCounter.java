package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Количество сессий сна с плохим качеством сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long badQualitySessionCounter = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult(DESCRIPTION, badQualitySessionCounter);
    }
}