package ru.javawebinar.topjava.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_USER, USER_ID);
        assertMatch(meal, userMealBreakfastFeb02);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getFromAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_ADMIN, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_USER, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_USER, USER_ID));
    }

    @Test
    public void deleteNotExisted() {
        assertThrows(NotFoundException.class, ()->service.delete(NOT_FOUND, USER_ID));

    }

    @Test
    public void deleteForAnotherUser() {
        assertThrows(NotFoundException.class, ()->service.delete(MEAL_ID_ADMIN, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenDates = service.getBetweenInclusive(LocalDate.parse("2023-02-03"), LocalDate.parse("2023-02-03"), USER_ID);
        assertMatch(betweenDates, userMealDinnerFeb03, userMealLunchFeb03, userMealBreakfastFeb03, userMealBorderFeb03);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(
                all,
                userMealDinnerFeb03,
                userMealLunchFeb03,
                userMealBreakfastFeb03,
                userMealBorderFeb03,
                userMealDinnerFeb02,
                userMealLunchFeb02,
                userMealBreakfastFeb02
        );
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID_USER, USER_ID), updated);
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdatedNotExisted(), USER_ID));
    }

    @Test
    public void updateForAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(newMeal, created);
        assertMatch(service.get(newId, USER_ID), created);
    }

    @Test
    public void duplicateDateTime() {
        assertThrows(DataAccessException.class, () ->
                service.create(
                        new Meal(
                                null,
                                LocalDateTime.parse("2023-02-02T10:00:00"),
                                "Duplicate DateTime User Завтрак",
                                1000
                        ),
                        USER_ID
                )
        );
    }
}