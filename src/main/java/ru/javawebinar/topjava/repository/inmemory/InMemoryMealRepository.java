package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.*;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, MealRepository> repository = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(meal -> save(meal, user1.getId()));
        save(new Meal(LocalDateTime.of(2021, Month.APRIL, 1, 9, 0), "Админ тест 1", 300), user2.getId());
        save(new Meal(LocalDateTime.of(2021, Month.APRIL, 1, 12, 0), "Админ тест 1", 400), user2.getId());
        save(new Meal(LocalDateTime.of(2021, Month.APRIL, 1, 18, 0), "Админ тест 1", 500), user2.getId());
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MealRepository meals = repository.computeIfAbsent(userId, uid -> new MealRepository());
        return meals.saveMeal(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        MealRepository meals = repository.get(userId);
        return meals != null && meals.delete(id);

    }

    @Override
    public Meal get(int id, int userId) {
        MealRepository meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllByDateTime(int userId,
                                       LocalDateTime startDateTime,
                                       LocalDateTime endDateTime) {
        return getAllByPredicate(
                userId,
                meal -> DateTimeUtil.isBetweenHalfOpen(
                        meal.getDateTime(),
                        startDateTime,
                        endDateTime)
        );
    }

    private List<Meal> getAllByPredicate(int userId, Predicate<Meal> filter) {
        MealRepository meals = repository.get(userId);
        return meals == null ? Collections.emptyList() :
                meals.getMeals().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .filter(filter)
                        .collect(Collectors.toList());
    }

    static class MealRepository {
        private static final AtomicInteger counter = new AtomicInteger(0);
        private final Map<Integer, Meal> map = new ConcurrentHashMap<>();

        public Meal saveMeal(Meal meal) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                map.put(meal.getId(), meal);
                return meal;
            }
            return map.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }

        public boolean delete(int id) {
            return map.remove(id) != null;
        }

        public Meal get(int id) {
            return map.get(id);
        }

        public Collection<Meal> getMeals() {
            return map.values();
        }
    }
}

