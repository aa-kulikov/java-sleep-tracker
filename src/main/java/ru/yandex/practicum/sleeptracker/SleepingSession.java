package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {

    private final LocalDateTime dateTimeStart;
    private final LocalDateTime dateTimeEnd;
    private final SleepQuality quality;

    public SleepingSession(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, SleepQuality quality) {
        if (dateTimeStart == null) {
            throw new IllegalArgumentException("Время начала сна не может быть null");
        }
        if (dateTimeEnd == null) {
            throw new IllegalArgumentException("Время окончания сна не может быть null");
        }
        if (quality == null) {
            throw new IllegalArgumentException("Качество сна не может быть null");
        }
        if (!dateTimeEnd.isAfter(dateTimeStart)) {
            throw new IllegalArgumentException("Время окончания сна должно быть позже времени начала");
        }

        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.quality = quality;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public Duration getDuration() {
        return Duration.between(dateTimeStart, dateTimeEnd);
    }
}