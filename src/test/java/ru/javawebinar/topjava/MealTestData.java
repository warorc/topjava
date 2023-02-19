package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID_USER = START_SEQ + 3;
    public static final int MEAL_ID_ADMIN = MEAL_ID_USER + 7;
    public static final int NOT_FOUND = 2;

    public static final Meal userMealBreakfastFeb02 = new Meal(
            MEAL_ID_USER,
            LocalDateTime.parse("2023-02-02T10:00:00"),
            "Завтрак User",
            500
    );
    public static final Meal userMealLunchFeb02 = new Meal(
            MEAL_ID_USER + 1,
            LocalDateTime.parse("2023-02-02T13:00:00"),
            "Обед User",
            1000
    );
    public static final Meal userMealDinnerFeb02 = new Meal(
            MEAL_ID_USER + 2,
            LocalDateTime.parse("2023-02-02T20:00:00"),
            "Ужин User",
            500
    );
    public static final Meal userMealBorderFeb03 = new Meal(
            MEAL_ID_USER + 3,
            LocalDateTime.parse("2023-03-03T00:00:00"),
            "Еда на граничное значение User",
            100
    );
    public static final Meal userMealBreakfastFeb03 = new Meal(
            MEAL_ID_USER + 4,
            LocalDateTime.parse("2023-02-03T10:00:00"),
            "Завтрак User",
            1000
    );
    public static final Meal userMealLunchFeb03 = new Meal(
            MEAL_ID_USER + 5,
            LocalDateTime.parse("2023-02-03T13:00:00"),
            "Обед User",
            500
    );
    public static final Meal userMealDinnerFeb03 = new Meal(
            MEAL_ID_USER + 6,
            LocalDateTime.parse("2023-02-03T20:00:00"),
            "Ужин User",
            410
    );
    public static final Meal adminMealBreakfastFeb02 = new Meal(
            MEAL_ID_ADMIN,
            LocalDateTime.parse("2023-02-02T10:00:00"),
            "Завтрак Admin",
            500
    );
    public static final Meal adminMealLunchFeb02 = new Meal(
            MEAL_ID_ADMIN + 1,
            LocalDateTime.parse("2023-02-02T13:00:00"),
            "Обед Admin",
            1000
    );
    public static final Meal adminMealDinnerFeb02 = new Meal(
            MEAL_ID_ADMIN + 2,
            LocalDateTime.parse("2023-02-02T20:00:00"),
            "Ужин Admin",
            500
    );
    public static final Meal adminMealBorderFeb03 = new Meal(
            MEAL_ID_ADMIN + 3,
            LocalDateTime.parse("2023-03-03T00:00:00"),
            "Еда на граничное значение Admin",
            100
    );
    public static final Meal adminMealBreakfastFeb03 = new Meal(
            MEAL_ID_ADMIN + 4,
            LocalDateTime.parse("2023-02-03T10:00:00"),
            "Завтрак Admin",
            1000
    );
    public static final Meal adminMealLunchFeb03 = new Meal(
            MEAL_ID_ADMIN + 5,
            LocalDateTime.parse("2023-02-03T13:00:00"),
            "Обед Admin",
            500
    );
    public static final Meal adminMealDinnerFeb03 = new Meal(
            MEAL_ID_ADMIN + 6,
            LocalDateTime.parse("2023-02-03T20:00:00"),
            "Ужин Admin",
            410
    );

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "New Meal", 300);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMealBreakfastFeb02);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("Updated Завтрак User");
        updated.setCalories(100);
        return updated;
    }

    public static Meal getUpdatedNotExisted() {
        Meal updated = new Meal(userMealBreakfastFeb02);
        updated.setId(NOT_FOUND);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("Updated Завтрак User");
        updated.setCalories(100);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }

}
