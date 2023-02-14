package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.Filter;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
        MealsUtil.mealsUser.forEach(meal -> create(meal, 1));
        MealsUtil.mealsAdmin.forEach(meal -> create(meal, 2));
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getAllFilteredByDate(int userId, Filter filter) {
        return (repository.getAllFilteredByDate(
                userId,
                filter.getStartDate(),
                filter.getEndDate()
        ));
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}
