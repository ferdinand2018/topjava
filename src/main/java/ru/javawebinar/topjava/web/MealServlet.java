package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.impl.MealRepositoryInMemoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {

    private MealRepository repository;
    private static final String MEAL_LIST = "/meals.jsp";
    private static final String MEAL_INSERT_OR_EDIT = "/editMeal.jsp";

    @Override
    public void init() {
        repository = new MealRepositoryInMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "default" : action) {
            case "delete":
                int deletedId = Integer.parseInt(request.getParameter("id"));
                repository.delete(deletedId);
                response.sendRedirect("meals");
                break;
            case "insert":
                final Meal newMeal = new Meal(LocalDateTime.now(), "", 1);
                request.setAttribute("meal", newMeal);
                request.getRequestDispatcher(MEAL_INSERT_OR_EDIT).forward(request, response);
                break;
            case "edit":
                final Meal editMeal = repository.getById(
                        Integer.parseInt(request.getParameter("id"))
                );
                request.setAttribute("meal", editMeal);
                request.getRequestDispatcher(MEAL_INSERT_OR_EDIT).forward(request, response);
                break;
            case "default":
            default:
                request.setAttribute("meals", MealsUtil.getTos(
                        repository.getAll(),
                        MealsUtil.DEFAULT_CALORIES_PER_DAY)
                );
                request.getRequestDispatcher(MEAL_LIST).forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String mealId = request.getParameter("id");
        Meal meal = new Meal(
                mealId.isEmpty() ? null : Integer.parseInt(mealId),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        if (mealId.isEmpty()) {
            repository.add(meal);
        } else {
            repository.edit(meal);
        }
        response.sendRedirect("meals");
    }
}
