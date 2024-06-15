package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {

    private static final String MEAL_LIST = "/meals.jsp";
    private static final String MEAL_INSERT_OR_EDIT = "/edit.jsp";

    public static final int caloriesPerDay = 2000;

    public static List<MealTo> getMealsList() {
        return MealsUtil.filteredByStreams(
                MealsUtil.meals,
                LocalTime.of(0, 0),
                LocalTime.of(23, 0),
                caloriesPerDay);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            deleteMealById(id);
            forward = MEAL_LIST;
            request.setAttribute("mealsList", getMealsList());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = MEAL_INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = getMealById(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeal")) {
            forward = MEAL_LIST;
            request.setAttribute("mealsList", getMealsList());
        } else {
            forward = MEAL_INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String mealId = request.getParameter("id");
        Integer i = createMaxId();
        Meal meal = new Meal(
                i++,
                getDate(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
        if (isMeaIdEmpty(mealId)) {
            MealsUtil.meals.add(meal);
        } else {
            editMeal(Integer.parseInt(mealId), meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST);
        request.setAttribute("mealsList", getMealsList());
        view.forward(request, response);
    }

    private Integer createMaxId() {
        return Collections.max(MealsUtil.meals.stream()
                .map(Meal::getId)
                .collect(Collectors.toList()));
    }

    private void deleteMealById(int id) {
        try {
            MealsUtil.meals.remove(id);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void editMeal(int id, Meal meal) {
        try {
            MealsUtil.meals.set(id, meal);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private Meal getMealById(int id) {
        return MealsUtil.meals.get(id);
    }

    private boolean isMeaIdEmpty(String mealId) {
        return mealId == null || mealId.isEmpty();
    }

    private LocalDateTime getDate(String requestParam) throws ServletException {
        try {
            return LocalDateTime.parse(requestParam, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
