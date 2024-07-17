package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getWithUser() {
        Meal adminMeal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(adminMeal, MealTestData.adminMeal1);
        USER_MATCHER.assertMatch(adminMeal.getUser(), UserTestData.admin);
    }

    @Test
    public void getWithUserNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithUser(MealTestData.NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void getWithUserNotOwn() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithUser(MealTestData.MEAL1_ID, ADMIN_ID));
    }
}
