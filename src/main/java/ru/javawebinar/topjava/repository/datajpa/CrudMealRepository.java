package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Meal m WHERE m.user.id=?2 AND m.id=?1")
    int delete(int id, int userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.user.id=?2 AND m.id=?1")
    Optional<Meal> getWithUser(int id, int userId);

    List<Meal> getAllByUserId(int userId, Sort sort);

    List<Meal> getAllByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserId(
            LocalDateTime start,
            LocalDateTime end,
            int userId,
            Sort sort
    );
}
