package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int USER = 100000;
    public static final int ADMIN = 100001;

    public static final Meal USER_MAEL = new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Тестовый завтрак", 500);
    public static final Meal ADMIN_MAEL = new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 1, 10, 0), "Тестовый завтрак админа", 321);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
