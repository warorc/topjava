package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private static final Sort BY_DATE_TIME = Sort.by(Sort.Direction.DESC, "dateTime");

    private final CrudMealRepository crudMealRepository;

    private final CrudUserRepository crudUserRepository;

    @PersistenceContext
    private EntityManager em;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(crudUserRepository.getReferenceById(userId));
            return crudMealRepository.save(meal);
        }
        if (get(meal.id(), userId) == null)
            return null;
        else {
            meal.setUser(crudUserRepository.getReferenceById(userId));
            return crudMealRepository.save(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Optional<Meal> meal = crudMealRepository.findById(id).filter((m) -> m.getUser().getId() == userId);
        return meal.isEmpty() ? null : meal.get();
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return crudMealRepository.getWithUser(id, userId).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository
                .getAllByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserId(
                        startDateTime,
                        endDateTime,
                        userId
                );
    }
}
