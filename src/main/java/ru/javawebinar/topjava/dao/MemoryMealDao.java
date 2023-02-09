package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealDao implements Dao<Meal> {
    private final Map<Integer, Meal> mapOfMeals = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    public MemoryMealDao() {
        List<Meal> meals = Arrays.asList(
                new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        meals.forEach(this::add);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mapOfMeals.values());
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(counter.incrementAndGet());
        mapOfMeals.put(meal.getId(), meal);
        return meal;
    }

    public void delete(int itemId) {
        mapOfMeals.remove(itemId);
    }

    @Override
    public Meal update(Meal updatedMeal) {
        int id = updatedMeal.getId();
        if (mapOfMeals.replace(id, mapOfMeals.get(id), updatedMeal)) {
            return updatedMeal;
        }
        return null;
    }

    @Override
    public Meal getById(int id) {
        return mapOfMeals.get(id);
    }
}
