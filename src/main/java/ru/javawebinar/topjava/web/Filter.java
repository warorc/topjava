package ru.javawebinar.topjava.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Filter {
    private LocalDateTime startDate = null;
    private LocalDateTime endDate = null;
    private LocalTime startTime = null;
    private LocalTime endTime = null;

    public Filter() {
    }

    public Filter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startDate = startDate != null ? startDate.atStartOfDay() : null;
        this.endDate = endDate != null ? endDate.atStartOfDay() : null;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
