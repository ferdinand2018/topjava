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
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

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
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateMailCreate() {
        Meal meal = new Meal(null, userMeal1.getDateTime(), "Тест", 1000);
        assertThrows(DataAccessException.class, () ->
                service.create(meal, USER_ID));
    }

    @Test
    public void update() {
        service.update(getUpdated(), USER_ID);
        assertMatch(service.get(getUpdated().getId(), USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwned() {
        Meal meal = new Meal(userMeal1);
        assertThrows(NotFoundException.class, () -> service.update(meal, ADMIN_ID));
    }

    @Test
    public void delete() {
        Integer id = userMeal1.getId();
        service.delete(id, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(id, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deletedNotOwned() {
        assertThrows(NotFoundException.class, () -> service.delete(userMeal2.getId(), ADMIN_ID));
    }

    @Test
    public void get() {
        assertMatch(service.get(userMeal6.getId(), USER_ID), userMeal6);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwned() {
        assertThrows(NotFoundException.class, () -> service.get(userMeal4.getId(), ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> expected = Arrays.asList(adminMeal10, adminMeal9, adminMeal8);
        List<Meal> actual = service.getAll(ADMIN_ID);
        assertMatch(actual, expected);
    }

    @Test
    public void getAllNotFound() {
        List<Meal> actual = service.getAll(UserTestData.NOT_FOUND);
        assertMatch(actual, EMPTY_LIST);
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expected = Arrays.asList(userMeal3, userMeal2, userMeal1);
        List<Meal> actual = service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30),
                USER_ID
        );
        assertMatch(actual, expected);
    }

    @Test
    public void getBetweenInclusiveWhenStartDateAfterBeforeDate() {
        List<Meal> actual = service.getBetweenInclusive(
                LocalDate.of(2021, Month.FEBRUARY, 1),
                LocalDate.of(2020, Month.FEBRUARY, 1),
                ADMIN_ID
        );
        assertMatch(actual, EMPTY_LIST);
    }

    @Test
    public void getBetweenInclusiveWithoutFilter() {
        List<Meal> expected = Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
        List<Meal> actual = service.getBetweenInclusive(null, null, USER_ID);
        assertMatch(actual, expected);
    }

    @Test
    public void getBetweenInclusiveNotFound() {
        List<Meal> actual = service.getBetweenInclusive(null, null, UserTestData.NOT_FOUND);
        assertMatch(actual, EMPTY_LIST);
    }
}
