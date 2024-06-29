package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final List<Meal> EMPTY_LIST = Collections.emptyList();
    public static final int MEAL_ID = START_SEQ  + 3;
    public static final int MEAL_ADMIN_ID = START_SEQ  + 10;
    public static final int NOT_FOUND = 10;

    public static final Meal userMeal1 = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal adminMeal8 = new Meal(MEAL_ADMIN_ID, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак админа", 321);
    public static final Meal adminMeal9 = new Meal(MEAL_ADMIN_ID + 1, LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Обед админа", 1155);
    public static final Meal adminMeal10 = new Meal(MEAL_ADMIN_ID + 2, LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Ужин админа", 121);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Тестовый завтрак", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal5);
        updated.setDateTime(LocalDateTime.of(2024, Month.APRIL, 11, 11, 0));
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
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
