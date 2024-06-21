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
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.*;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, InMemoryCommonRepository<Meal>> repository = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(meal -> save(meal, USER_1.getId()));
        save(new Meal(LocalDateTime.of(2021, Month.APRIL, 1, 9, 0), "Админ тест 1", 300), USER_2.getId());
        save(new Meal(LocalDateTime.of(2021, Month.APRIL, 1, 12, 0), "Админ тест 1", 400), USER_2.getId());
        save(new Meal(LocalDateTime.of(2021, Month.APRIL, 1, 18, 0), "Админ тест 1", 500), USER_2.getId());
    }

    @Override
    public Meal save(Meal meal, int userId) {
        InMemoryCommonRepository<Meal> meals = repository.computeIfAbsent(userId, uid ->
                new InMemoryCommonRepository<>());
        return meals.saveMeal(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryCommonRepository<Meal> meals = repository.get(userId);
        if (meals != null) {
            meals.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        InMemoryCommonRepository<Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        InMemoryCommonRepository<Meal> meals = repository.get(userId);
        return meals == null ? Collections.emptyList() :
                meals.getMeal().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllByDateTime(int userId,
                                       LocalDateTime startDateTime,
                                       LocalDateTime endDateTime) {
        List<Meal> meals = getAll(userId);
        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(
                        meal.getDateTime(),
                        startDateTime,
                        endDateTime))
                .collect(Collectors.toList());
    }
}

