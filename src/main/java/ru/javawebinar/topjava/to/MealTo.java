package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class MealTo {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    @JsonCreator
    public MealTo(
            @JsonProperty("id") Integer id,
            @JsonProperty("dateTime") LocalDateTime dateTime,
            @JsonProperty("description") String description,
            @JsonProperty("calories") int calories,
            @JsonProperty("excess") boolean excess
    ) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        MealTo object = (MealTo) obj;
        return Objects.equals(this.id, object.getId())
                && this.dateTime.equals(object.getDateTime())
                && this.description.equals(object.getDescription())
                && this.calories == (object.getCalories())
                && this.excess == (object.isExcess());
    }
}
