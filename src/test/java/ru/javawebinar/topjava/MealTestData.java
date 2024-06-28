package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int NOT_FOUND_USER = 0;

    public static Meal userMeal = new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Тестовый завтрак", 500);
    public static Meal userMeal1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Тестовый завтрак 1", 500);
    public static Meal userMeal2 = new Meal(LocalDateTime.of(2019, Month.JUNE, 14, 10, 0), "Тестовый завтрак 2", 500);
    public static Meal userMeal3 = new Meal(LocalDateTime.of(2017, Month.NOVEMBER, 14, 19, 2), "Тестовый завтрак 3", 500);
    public static Meal adminMeal = new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 1, 10, 0), "Тестовый завтрак админа", 321);


    public static Meal meal1 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static Meal meal2 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static Meal meal3 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static Meal meal4 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static Meal meal5 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static Meal meal6 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static Meal meal7 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static Meal meal8 = new Meal(100004, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак админа", 321);
    public static Meal meal9 = new Meal(100004, LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Обед админа", 1155);
    public static Meal meal10 = new Meal(100004, LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Ужин админа", 1155);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
