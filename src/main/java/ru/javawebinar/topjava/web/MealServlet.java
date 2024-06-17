package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final String MEAL_LIST = "/meals.jsp";
    private static final String MEAL_INSERT_OR_EDIT = "/editMeal.jsp";
    private MealRepository repository;

    @Override
    public void init() {
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "default" : action) {
            case "delete":
                int deletedId = Integer.parseInt(request.getParameter("id"));
                repository.delete(deletedId);
                log.info("Deleted meal with ID = {}", deletedId);
                response.sendRedirect("meals");
                break;
            case "insert":
                final Meal newMeal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1);
                request.setAttribute("meal", newMeal);
                request.getRequestDispatcher(MEAL_INSERT_OR_EDIT).forward(request, response);
                log.info("Creating new meal");
                break;
            case "edit":
                final Meal editMeal = repository.getById(
                        Integer.parseInt(request.getParameter("id"))
                );
                request.setAttribute("meal", editMeal);
                request.getRequestDispatcher(MEAL_INSERT_OR_EDIT).forward(request, response);
                log.info("Editing meal with ID = {}", editMeal.getId());
                break;
            case "default":
            default:
                log.info("Get all meals");
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
            log.info("Successfully sent insert request with body  = {}", meal);
        } else {
            repository.edit(meal);
            log.info("Successfully sent edit request with body = {}", meal);
        }
        response.sendRedirect("meals");
    }
}
