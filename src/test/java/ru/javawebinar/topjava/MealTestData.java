package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 3;
    public static final int ADMIN_MEAL_ID = USER_MEAL_ID + 7;
    public static final int NOT_FOUND = 2;

    public static final Meal userMealBreakfastFeb02 = new Meal(
            USER_MEAL_ID,
            LocalDateTime.of(2023, 2, 2, 10, 0),
            "Завтрак User",
            500
    );
    public static final Meal userMealLunchFeb02 = new Meal(
            USER_MEAL_ID + 1,
            LocalDateTime.of(2023, 2, 2, 13, 0),
            "Обед User",
            1000
    );
    public static final Meal userMealDinnerFeb02 = new Meal(
            USER_MEAL_ID + 2,
            LocalDateTime.of(2023, 2, 2, 20, 0),
            "Ужин User",
            500
    );
    public static final Meal userMealBorderFeb03 = new Meal(
            USER_MEAL_ID + 3,
            LocalDateTime.of(2023, 2, 3, 0, 0),
            "Еда на граничное значение User",
            100
    );
    public static final Meal userMealBreakfastFeb03 = new Meal(
            USER_MEAL_ID + 4,
            LocalDateTime.of(2023, 2, 3, 10, 0),
            "Завтрак User",
            1000
    );
    public static final Meal userMealLunchFeb03 = new Meal(
            USER_MEAL_ID + 5,
            LocalDateTime.of(2023, 2, 3, 13, 0),
            "Обед User",
            500
    );
    public static final Meal userMealDinnerFeb03 = new Meal(
            USER_MEAL_ID + 6,
            LocalDateTime.of(2023, 2, 3, 20, 0),
            "Ужин User",
            410
    );
    public static final Meal adminMealBreakfastFeb02 = new Meal(
            ADMIN_MEAL_ID,
            LocalDateTime.of(2023, 2, 2, 10, 0),
            "Завтрак Admin",
            500
    );
    public static final Meal adminMealLunchFeb02 = new Meal(
            ADMIN_MEAL_ID + 1,
            LocalDateTime.of(2023, 2, 2, 13, 0),
            "Обед Admin",
            1000
    );
    public static final Meal adminMealDinnerFeb02 = new Meal(
            ADMIN_MEAL_ID + 2,
            LocalDateTime.of(2023, 2, 2, 20, 0),
            "Ужин Admin",
            500
    );
    public static final Meal adminMealBorderFeb03 = new Meal(
            ADMIN_MEAL_ID + 3,
            LocalDateTime.of(2023, 2, 3, 0, 0),
            "Еда на граничное значение Admin",
            100
    );
    public static final Meal adminMealBreakfastFeb03 = new Meal(
            ADMIN_MEAL_ID + 4,
            LocalDateTime.of(2023, 2, 3, 10, 0),
            "Завтрак Admin",
            1000
    );
    public static final Meal adminMealLunchFeb03 = new Meal(
            ADMIN_MEAL_ID + 5,
            LocalDateTime.of(2023, 2, 3, 13, 0),
            "Обед Admin",
            500
    );
    public static final Meal adminMealDinnerFeb03 = new Meal(
            ADMIN_MEAL_ID + 6,
            LocalDateTime.of(2023, 2, 3, 20, 0),
            "Ужин Admin",
            410
    );

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, 2, 3, 21, 0), "New Meal", 300);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMealBreakfastFeb02);
        updated.setDateTime(LocalDateTime.of(2023, 2, 5, 20, 0));
        updated.setDescription("Updated Завтрак User");
        updated.setCalories(100);
        return updated;
    }

    public static Meal getUpdatedNotExisted() {
        Meal updated = new Meal(userMealBreakfastFeb02);
        updated.setId(NOT_FOUND);
        updated.setDateTime(LocalDateTime.of(2023, 2, 5, 20, 0));
        updated.setDescription("Updated Завтрак User");
        updated.setCalories(100);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }


    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
