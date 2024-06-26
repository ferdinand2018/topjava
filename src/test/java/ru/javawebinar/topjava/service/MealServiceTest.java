package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(USER_MAEL, USER);
        Integer newId = created.getId();
        Meal newMeal = USER_MAEL;
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER), newMeal);
    }

    @Test
    public void update() {
        Meal created = service.create(USER_MAEL_2, USER);
        Integer newId = created.getId();
        Meal updateMeal = new Meal(newId, LocalDateTime.of(2018, Month.FEBRUARY, 25, 7, 0), "Обновлённый завтрак", 456);
        service.update(updateMeal, USER);
        assertMatch(service.get(newId, USER), updateMeal);
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(USER_MAEL_1, USER));
    }

    @Test
    public void delete() {
        Meal created = service.create(ADMIN_MAEL, USER);
        Integer newId = created.getId();
        service.delete(newId, USER);
        assertThrows(NotFoundException.class, () -> service.get(newId, USER));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER));
    }

    @Test
    public void get() {
        Meal created = service.create(USER_MAEL_3, USER);
        assertMatch(service.get(USER_MAEL_3.getId(), USER), created);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER));
    }
}
