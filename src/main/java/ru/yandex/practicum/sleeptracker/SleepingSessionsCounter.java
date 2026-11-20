package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepingSessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Общее количество сессий сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long numberOfSleepingSessions = sessions.size();
        return new SleepAnalysisResult(DESCRIPTION, numberOfSleepingSessions);
    }
}