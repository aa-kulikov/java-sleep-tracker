package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class SleepAnalyzer {

    private final List<Function<List<SleepingSession>, SleepAnalysisResult>> functions;

    public SleepAnalyzer() {
        this(List.of(
                new SleepingSessionsCounter(),
                new MinSleepingTimeCalculator(),
                new MaxSleepingTimeCalculator(),
                new AverageSleepingTimeCalculator(),
                new BadQualitySessionsCounter(),
                new SleeplessNightCounter(),
                new ChronotypeResolver()
        ));
    }

    public SleepAnalyzer(List<Function<List<SleepingSession>, SleepAnalysisResult>> functions) {
        if (functions == null) {
            throw new SleepAnalysisException("Список функций не должен быть null");
        }

        List<Function<List<SleepingSession>, SleepAnalysisResult>> validFunctions = functions.stream()
                .filter(Objects::nonNull)
                .toList();

        if (validFunctions.isEmpty()) {
            throw new SleepAnalysisException("Список функций не содержит ни одной корректной функции");
        }

        this.functions = validFunctions;
    }

    public List<SleepAnalysisResult> analyze(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            throw new SleepAnalysisException("Список сессий сна не может быть null или пустым");
        }

        List<SleepingSession> validSessions = sessions.stream()
                .filter(Objects::nonNull)
                .toList();

        if (validSessions.isEmpty()) {
            throw new SleepAnalysisException("Список сессий сна не содержит ни одной корректной записи");
        }

        return functions.stream()
                .map(function -> executeFunction(function, validSessions))
                .toList();
    }

    private SleepAnalysisResult executeFunction(Function<List<SleepingSession>, SleepAnalysisResult> function,
                                                 List<SleepingSession> sessions) {
        try {
            SleepAnalysisResult result = function.apply(sessions);
            if (result == null) {
                throw new SleepAnalysisException("Функция " + function.getClass().getSimpleName() + " вернула null");
            }
            return result;
        } catch (SleepAnalysisException e) {
            throw e;
        } catch (Exception exp) {
            throw new SleepAnalysisException(
                    "Ошибка при выполнении функции " + function.getClass().getSimpleName() + ": " + exp.getMessage()
            );
        }
    }
}