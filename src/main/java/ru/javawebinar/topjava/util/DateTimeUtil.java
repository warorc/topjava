package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T startTime, T endTime) {
        if (startTime == null && endTime == null)
            return true;
        else if (startTime == null)
            return lt instanceof LocalDateTime ? lt.compareTo(endTime) <= 0 : lt.compareTo(endTime) < 0;
        else if (endTime == null)
            return lt.compareTo(startTime) >= 0;
        else
            return lt instanceof LocalDateTime ? lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0 : lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
