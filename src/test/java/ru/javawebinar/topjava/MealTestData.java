package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int USER = 100000;
    public static final int ADMIN = 100001;
    public static final int NOT_FOUND = 0;

    public static final Meal USER_MAEL = new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Тестовый завтрак", 500);
    public static final Meal USER_MAEL_1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Тестовый завтрак 1", 500);
    public static final Meal USER_MAEL_2 = new Meal(LocalDateTime.of(2019, Month.JUNE, 14, 10, 0), "Тестовый завтрак 2", 500);
    public static final Meal USER_MAEL_3 = new Meal(LocalDateTime.of(2017, Month.NOVEMBER, 14, 19, 2), "Тестовый завтрак 3", 500);
    public static final Meal ADMIN_MAEL = new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 1, 10, 0), "Тестовый завтрак админа", 321);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
