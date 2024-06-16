package ru.javawebinar.topjava.repository.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryInMemoryImpl implements MealRepository {

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public Meal getById(int id) {
        return repository.get(id);
    }

    @Override
    public void add(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        }
    }

    @Override
    public void edit(Meal meal) {
        repository.computeIfPresent(meal.getId(), (a, b) -> meal);
    }

    @Override
    public void delete(Integer id) {
        repository.remove(id);
    }
}
