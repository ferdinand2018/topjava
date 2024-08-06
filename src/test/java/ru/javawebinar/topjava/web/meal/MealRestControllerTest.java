package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.createTo;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URI = MealRestController.REST_URL + "/";
    private static final String MEAL1_ID_URI = REST_URI + MEAL1_ID;

    @Autowired
    private MealService service;

    @Test
    void get() throws Exception {
        getActions(MEAL1_ID_URI, meal1);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(MEAL1_ID_URI))
                .andExpect(status().isNoContent());

        Assertions.assertThrows(
                NotFoundException.class,
                () -> service.get(MEAL1_ID, USER_ID)
        );
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(mealsTo));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal meal = getNew();

        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal))
        ).andExpect(status().isCreated());

        Meal created = MEAL_MATCHER.readFromJson(actions);
        int id = created.id();
        meal.setId(id);

        getActions(REST_URI + id, meal);

        MEAL_MATCHER.assertMatch(created, meal);
        MEAL_MATCHER.assertMatch(service.get(id, USER_ID), meal);
    }

    @Test
    void update() throws Exception {
        Meal meal = getUpdated();

        perform(MockMvcRequestBuilders.put(MEAL1_ID_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal))
        ).andExpect(status().isNoContent());

        getActions(MEAL1_ID_URI, meal);
    }

    @ParameterizedTest
    @MethodSource("getBetweenTestProvider")
    void getBetween(String startDate, String startTime, String endDate, String endTime, List<MealTo> meals) throws Exception {

        perform(MockMvcRequestBuilders.get(REST_URI + "between")
                .param("startDate", startDate).param("startTime", startTime)
                .param("endDate", endDate).param("endTime", endTime)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TO_MATCHER.contentJson(meals));
    }

    private static Stream<Arguments> getBetweenTestProvider() {
        return Stream.of(
                arguments(
                        "2020-01-30",
                        "00:00",
                        "2020-01-31",
                        "12:00",
                        List.of(
                                createTo(meal5, true),
                                createTo(meal4, true),
                                createTo(meal1, false)
                        )
                ),
                arguments(
                        "2020-01-30",
                        "00:00",
                        null,
                        null,
                        List.of(
                                createTo(meal7, true),
                                createTo(meal6, true),
                                createTo(meal5, true),
                                createTo(meal4, true),
                                createTo(meal3, false),
                                createTo(meal2, false),
                                createTo(meal1, false)
                        )
                ),
                arguments(
                        "",
                        "",
                        "",
                        "",
                        List.of(
                                createTo(meal7, true),
                                createTo(meal6, true),
                                createTo(meal5, true),
                                createTo(meal4, true),
                                createTo(meal3, false),
                                createTo(meal2, false),
                                createTo(meal1, false)
                        )
                ),
                arguments(
                        "",
                        "",
                        null,
                        null,
                        List.of(
                                createTo(meal7, true),
                                createTo(meal6, true),
                                createTo(meal5, true),
                                createTo(meal4, true),
                                createTo(meal3, false),
                                createTo(meal2, false),
                                createTo(meal1, false)
                        )
                ),
                arguments(
                        "2030-01-30",
                        "00:00",
                        "2030-01-31",
                        "00:00",
                        List.of()
                )
        );
    }

    private ResultActions getActions(String mealUri, Meal expected) throws Exception {
        return perform(MockMvcRequestBuilders.get(mealUri))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(expected));
    }
}
