package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
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
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final String MEAL_LIST = "/meals.jsp";
    private static final String MEAL_INSERT_OR_EDIT = "/edit.jsp";

    public static final int caloriesPerDay = 2000;
    private MealDao dao;

    public MealServlet() {
        super();
        dao = new MealDao();
    }

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
            dao.deleteMeal(id);
            forward = MEAL_LIST;
            request.setAttribute("mealsList", getMealsList());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = MEAL_INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = dao.getMealById(id);
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
        LocalDateTime dateTime = getDate(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (isMeaIdEmpty(mealId)) {
            dao.addMeal(dateTime, description, calories);
        } else {
            dao.updateMeal(Integer.parseInt(mealId), dateTime, description, calories);
        }

        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST);
        request.setAttribute("mealsList", getMealsList());
        view.forward(request, response);
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
