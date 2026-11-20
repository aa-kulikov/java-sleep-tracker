package ru.yandex.practicum.sleeptracker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_FILE = "Errors.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void log(String message) {
        String dateTime = LocalDateTime.now().format(DATE_TIME_FORMATTER);

        try (PrintWriter log = new PrintWriter(new FileWriter(LOG_FILE, StandardCharsets.UTF_8, true), true)) {

            log.printf("%s ошибка: %s%n", dateTime, message);

        } catch (IOException exp) {
            throw new UncheckedIOException("Не удалось записать ошибку в файл " + LOG_FILE, exp);
        }
    }
}