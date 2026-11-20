package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class SleepTrackerApp {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу в аргументах запуска.");
            return;
        }

        Logger errLog = new Logger();

        try {
            analyzeFile(args[0], new SleepAnalyzer(), errLog).forEach(System.out::println);
        } catch (IOException | UncheckedIOException exp) {
            errLog.log("Ошибка чтения файла '" + args[0] + "': " + exp.getMessage());
            System.out.println("Не удалось прочитать файл. Подробности в лог-файле.");
        } catch (SleepAnalysisException exp) {
            errLog.log("Ошибка при анализе данных в файле '" + args[0] + "': " + exp.getMessage());
            System.out.println("Не удалось выполнить анализ сна. Подробности в лог-файле.");
        }
    }

    public static List<SleepAnalysisResult> analyzeFile(String filename, SleepAnalyzer analyzer, Logger errLog)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename), StandardCharsets.UTF_8)) {
            List<SleepingSession> sessions = reader.lines()
                    .filter(line -> !line.isBlank())
                    .map(line -> parseLine(line, errLog))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            return analyzer.analyze(sessions);
        }
    }

    static Optional<SleepingSession> parseLine(String line, Logger errLog) {
        try {
            String[] parts = line.split(";");
            if (parts.length != 3) {
                errLog.log("Пропущена некорректная строка (некорректное число полей): " + line);
                return Optional.empty();
            }

            LocalDateTime start = LocalDateTime.parse(parts[0].trim(), DATE_TIME_FORMATTER);
            LocalDateTime end = LocalDateTime.parse(parts[1].trim(), DATE_TIME_FORMATTER);
            SleepQuality quality = SleepQuality.valueOf(parts[2].trim().toUpperCase());

            return Optional.of(new SleepingSession(start, end, quality));
        } catch (Exception exp) {
            errLog.log("Пропущена некорректная строка: '" + line + "' (" + exp.getMessage() + ")");
            return Optional.empty();
        }
    }
}