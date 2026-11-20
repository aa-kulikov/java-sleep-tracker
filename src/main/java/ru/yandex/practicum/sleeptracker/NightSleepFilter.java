package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;

public class NightSleepFilter implements Predicate<SleepingSession> {

    private static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public boolean test(SleepingSession session) {
        LocalDate startDate = session.getDateTimeStart().toLocalDate();
        LocalDate endDate = session.getDateTimeEnd().toLocalDate();

        return startDate.datesUntil(endDate.plusDays(1)) //+1 чтобы включить в интервал правую границу
                .anyMatch(date -> overlapsNight(session, date));
    }

    static boolean overlapsNight(SleepingSession session, LocalDate date) {
        LocalDateTime nightStart = date.atTime(NIGHT_START);
        LocalDateTime nightEnd = date.atTime(NIGHT_END);

        return session.getDateTimeStart().isBefore(nightEnd) && session.getDateTimeEnd().isAfter(nightStart);
    }
}