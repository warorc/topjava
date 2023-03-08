package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
        List<Meal> meals = user.getMeals();
        MEAL_MATCHER.assertMatch(meals, MealTestData.meals);
    }

    @Test
    public void getWithEmptyMeals() {
        User user = service.getWithMeals(GUEST_ID);
        USER_MATCHER.assertMatch(user, UserTestData.guest);
        assert (user.getMeals().isEmpty());
    }

    @Test
    public void getWithMealsNonExistedUser() {
        assertThrows(NotFoundException.class, () -> service.getWithMeals(UserTestData.NOT_FOUND));
    }
}
