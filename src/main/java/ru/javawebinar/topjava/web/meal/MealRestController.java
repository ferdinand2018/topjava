package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Collection<Meal> getByUserId() {
        int userId = authUserId();
        log.info("get by id={}", userId);
        return service.getByUserId(userId);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public void create(Meal meal) {
        log.info("create {}", meal);
        service.create(meal, authUserId());
    }

    public void update(Meal meal) {
        int userId = authUserId();
        log.info("update {} with id={}", meal, userId);
        assureIdConsistent(meal, meal.getId());
        service.update(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }
}