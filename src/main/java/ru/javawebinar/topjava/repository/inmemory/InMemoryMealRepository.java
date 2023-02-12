package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.Filter;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> mealIdToUserId = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("Repository save {} with userId {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            mealIdToUserId.put(meal.getId(), userId);
            return meal;
        }
        return mealIdToUserId.get(meal.getId()) == null || mealIdToUserId.get(meal.getId()) != userId
                ? null
                : repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("Repository delete {}", id);
        return mealIdToUserId.get(id) != null && mealIdToUserId.get(id) == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("Repository get {}", id);
        return mealIdToUserId.get(id) == null || mealIdToUserId.get(id) != userId ? null : repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("Repository getAll for userId {}", userId);
        return repository.values().stream()
                .filter(meal -> mealIdToUserId.get(meal.getId()) != null && mealIdToUserId.get(meal.getId()) == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Collection<Meal> getAllFilteredByDateAndTime(int userId, Filter filter) {
        log.info("Repository getAll filtered by date and time for userId {}", userId);
        return getAll(userId).stream()
                .filter(meal ->
                        DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalDate().atStartOfDay(), filter.getStartDate(), filter.getEndDate()))
                .filter(meal ->
                        DateTimeUtil.isBetweenHalfOpen(meal.getTime(), filter.getStartTime(), filter.getEndTime()))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
