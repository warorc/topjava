package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T comparable, T start, T end) {
        if (start == null && end == null)
            return true;
        else if (start == null)
            return comparable.compareTo(end) < 0;
        else if (end == null)
            return comparable.compareTo(start) >= 0;
        else
            return comparable.compareTo(start) >= 0 && comparable.compareTo(end) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
