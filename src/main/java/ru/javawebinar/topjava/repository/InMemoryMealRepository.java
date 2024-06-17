package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::add);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public Meal getById(int id) {
        return repository.get(id);
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(counter.incrementAndGet());
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal edit(Meal meal) {
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }
}
