package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryCommonRepository<T> {
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

    public Collection<Meal> getMeal() {
        return map.values();
    }
}
