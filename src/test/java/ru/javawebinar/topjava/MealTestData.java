package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final List<Meal> EMPTY_LIST = Collections.emptyList();

    public static Meal meal1 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static Meal meal2 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static Meal meal3 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static Meal meal4 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static Meal meal5 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static Meal meal6 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static Meal meal7 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static Meal meal8 = new Meal(100010, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак админа", 321);
    public static Meal meal9 = new Meal(100011, LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Обед админа", 1155);
    public static Meal meal10 = new Meal(100012, LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Ужин админа", 1155);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Тестовый завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal5);
        updated.setDateTime(LocalDateTime.of(2024, Month.APRIL.getValue(), 11, 11, 0));
        updated.setDescription("Тестовый обновлённый завтрак");
        updated.setCalories(55);
        return updated;
    }

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
