package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {

    /**
     * Get all meals
     *
     * @return Meal list
     */
    Collection<Meal> getAll();

    /**
     * Get Meal by id
     *
     * @param id int
     * @return Meal
     */
    Meal getById(int id);

    /**
     * Add new meal
     *
     * @param meal Meal
     */
    void add(Meal meal);

    /**
     * Edit meal by id
     *
     * @param meal Meal
     */
    void edit(Meal meal);

    /**
     * Delete meal by id
     *
     * @param id Integer
     */
    void delete(Integer id);






}
