package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMeal implements CrudItems<Meal> {
    private final ConcurrentHashMap<Integer, Meal> mapOfMeals = new ConcurrentHashMap<>();

    public final AtomicInteger counter = new AtomicInteger(0);

    public MemoryMeal() {
        mapOfMeals.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mapOfMeals.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mapOfMeals.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mapOfMeals.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mapOfMeals.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mapOfMeals.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mapOfMeals.put(7, new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        this.counter.set(mapOfMeals.size());
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mapOfMeals.values());
    }

    @Override
    public Meal add(Meal meal) {
        counter.incrementAndGet();
        meal.setId(counter.get());
        mapOfMeals.put(counter.get(), meal);
        return meal;
    }

    public void delete(int itemId) {
        mapOfMeals.remove(itemId);
    }

    @Override
    public Meal update(Meal updatedMeal) {
        int id = updatedMeal.getId();
        mapOfMeals.put(id, updatedMeal);
        return updatedMeal;
    }

    public Meal getById(int id) {
        try {
            return mapOfMeals.get(id);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
