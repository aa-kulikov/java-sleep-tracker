package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeResolver implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Хронотип пользователя";

    private static final LocalTime LATE_TIME_TO_SLEEP = LocalTime.of(23, 0);
    private static final LocalTime LATE_TIME_TO_WAKEUP = LocalTime.of(9, 0);
    private static final LocalTime EARLY_TIME_TO_SLEEP = LocalTime.of(22, 0);
    private static final LocalTime EARLY_TIME_TO_WAKEUP = LocalTime.of(7, 0);
    private static final LocalTime MIDDLE_OF_DAY = LocalTime.of(12, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Map<Chronotype, Long> chronotypeCounts = sessions.stream()
                .filter(new NightSleepFilter())
                .map(this::resolveSessionChronotype)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (chronotypeCounts.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, Chronotype.PIGEON);
        }

        long maxCount = Collections.max(chronotypeCounts.values());

        List<Chronotype> topChronotypes = chronotypeCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .toList();

        Chronotype result;
        if (topChronotypes.size() == 1) {
            result = topChronotypes.getFirst();
        } else {
            result = Chronotype.PIGEON;
        }

        return new SleepAnalysisResult(DESCRIPTION, result);
    }

    private Chronotype resolveSessionChronotype(SleepingSession session) {
        LocalTime start = session.getDateTimeStart().toLocalTime();
        LocalTime end = session.getDateTimeEnd().toLocalTime();

        if ((start.isAfter(LATE_TIME_TO_SLEEP) || start.isBefore(NIGHT_END))
                && end.isAfter(LATE_TIME_TO_WAKEUP)) {
            return Chronotype.OWL;
        }

        if (start.isAfter(MIDDLE_OF_DAY)
                && start.isBefore(EARLY_TIME_TO_SLEEP)
                && end.isBefore(EARLY_TIME_TO_WAKEUP)) {
            return Chronotype.SKYLARK;
        }

        return Chronotype.PIGEON;
    }
}