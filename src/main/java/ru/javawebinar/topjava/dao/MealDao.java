package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class MealDao implements CrudItems<Meal> {
    private List<Meal> listOFMeals;

    public final AtomicInteger counter = new AtomicInteger(0);

    public MealDao() {
        this.listOFMeals = new CopyOnWriteArrayList<>(Arrays.asList(
                new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
        this.counter.set(this.listOFMeals.size());
    }

    @Override
    public List<Meal> getAllItems() {
        return listOFMeals;
    }

    @Override
    public void addItem(Meal meal) {
        if (meal.getId() == null) {
            counter.incrementAndGet();
            meal.setId(counter.get());
        }
        listOFMeals.add(meal);
    }

    public void deleteItem(int itemId) {
        this.listOFMeals = this.listOFMeals.stream().filter(meal -> meal.getId() != itemId).collect(Collectors.toList());
        counter.decrementAndGet();
    }

    @Override
    public void editItem(Meal editedItem) {
        this.listOFMeals = this.listOFMeals.stream().map(meal -> {
            if (Objects.equals(meal.getId(), editedItem.getId())) return editedItem;
            return meal;
        }).collect(Collectors.toList());
    }

    public Meal getItemById(Integer id) {
        return this.listOFMeals.stream().filter(meal -> meal.getId().equals(id)).findFirst().get();
    }
}
