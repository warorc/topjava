package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.Filter;

import java.util.Collection;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<Meal> getAll() {
        log.info("Rest controller getAll");
        return service.getAll(authUserId());
    }

    public Collection<MealTo> getAllTos() {
        log.info("Rest controller get all Meal Transfer Object");
        return MealsUtil.getTos(getAll(), authUserCaloriesPerDay());
    }

    public Collection<MealTo> getAllFilteredTos(Filter filter) {
        log.info("Rest controller get all Meal Transfer Object filtered by dates and times");
        return MealsUtil.getTos(service.getAllFilteredByDateAndTime(authUserId(), filter), authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("Rest controller get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("Rest controller create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("Rest controller delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("Rest controller update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }
}
