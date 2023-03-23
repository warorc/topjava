package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.util.List;

public class AbstractMealController {

    protected static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    protected final MealService service;

    public AbstractMealController(MealService service) {
        this.service = service;
    }

    protected List<Meal> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

    protected void delete(int id) {
        service.delete(id, SecurityUtil.authUserId());
    }

    protected List<Meal> getBetweenInclusive(LocalDate startDate, LocalDate endDate) {
        return service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
    }

    protected Meal get(int id) {
        return service.get(id, SecurityUtil.authUserId());
    }

    protected void update(Meal meal) {
        service.update(meal, SecurityUtil.authUserId());
    }

    protected Meal create(Meal meal) {
        return service.create(meal, SecurityUtil.authUserId());
    }


}
