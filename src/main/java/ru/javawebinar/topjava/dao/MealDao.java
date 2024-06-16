package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;

public class MealDao {
    private static List<Meal> meals = MealsUtil.meals;

    public void addMeal(LocalDateTime dateTime, String description, int calories) {
        int id = meals.get(meals.size() - 1).getId();
        id++;
        meals.add(new Meal(
                id,
                dateTime,
                description,
                calories
        ));
    }

    public void deleteMeal(Integer id) {
        meals.removeIf(nextMeal -> nextMeal.getId() == id);
    }

    public void updateMeal(int id, LocalDateTime dateTime, String description, int calories) {
        meals.set(
                meals.indexOf(new Meal(id, dateTime, description, calories)),
                new Meal(
                        id,
                        dateTime,
                        description,
                        calories
                ));
    }

    public Meal getMealById(int id) {
        for (Meal nextMeal : meals) {
            if (nextMeal.getId() == id) {
                return nextMeal;
            }
        }
        return null;
    }
}
