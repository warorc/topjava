package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Map<Integer, Meal>> userIdToMeals = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("Repository save {} with userId {}", meal, userId);
        Map<Integer, Map<Integer, Meal>> temporaryMap = userIdToMeals;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> newMealMap = new ConcurrentHashMap<>();
            newMealMap.put(meal.getId(), meal);
            temporaryMap.merge(
                    userId,
                    newMealMap,
                    (k, v) -> {
                        k.putAll(v);
                        return k;
                    });
            return meal;
        }
        if (temporaryMap.get(userId) == null || !temporaryMap.get(userId).containsKey(meal.getId())) return null;
        temporaryMap.computeIfPresent(userId, (id, mealMap) -> {
            mealMap.computeIfPresent(meal.getId(), (mealId, mealOld) -> meal);
            return mealMap;
        });
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Map<Integer, Meal>> temporaryMap = userIdToMeals;
        log.info("Repository delete {}", id);
        return temporaryMap.containsKey(userId)
                && temporaryMap.get(userId) != null
                && userIdToMeals.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Map<Integer, Meal>> temporaryMap = userIdToMeals;
        log.info("Repository get {} fir user {}", id, userId);
        return temporaryMap.get(userId) == null || temporaryMap.get(userId).get(id) == null
                ? null
                : temporaryMap.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("Repository getAll for userId {}", userId);
        return filteredByPredicate(userIdToMeals.get(userId).values(), meal -> true);
    }

    @Override
    public List<Meal> getAllFilteredByDate(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Repository getAll filtered by date and time for userId {}", userId);
        return filteredByPredicate(
                userIdToMeals.get(userId).values(),
                meal -> DateTimeUtil.isBetweenHalfOpen(
                        meal.getDateTime(),
                        startDate,
                        endDate != null ? endDate.plusDays(1) : null
                )
        );
    }

    private List<Meal> filteredByPredicate(Collection<Meal> meals, Predicate<Meal> filter) {
        return meals.stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}
