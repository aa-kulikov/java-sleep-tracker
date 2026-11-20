package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Количество бессонных ночей";
    private static final LocalTime MIDDLE_OF_DAY = LocalTime.of(12, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, 0L);
        }

        LocalDateTime earliestStart = sessions.stream()
                .map(SleepingSession::getDateTimeStart)
                .min(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDateTime latestEnd = sessions.stream()
                .map(SleepingSession::getDateTimeEnd)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDate firstNight;
        if (earliestStart.toLocalTime().isBefore(MIDDLE_OF_DAY)) {
            firstNight = earliestStart.toLocalDate();
        } else {
            firstNight = earliestStart.toLocalDate().plusDays(1);
        }

        LocalDate lastNight = latestEnd.toLocalDate();

        if (firstNight.isAfter(lastNight)) {
            return new SleepAnalysisResult(DESCRIPTION, 0L);
        }

        long sleeplessNights = firstNight.datesUntil(lastNight.plusDays(1))
                .filter(nightDate -> sessions.stream()
                        .noneMatch(session -> NightSleepFilter.overlapsNight(session, nightDate)))
                .count();

        return new SleepAnalysisResult(DESCRIPTION, sleeplessNights);
    }
}