package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;

import static ru.javawebinar.topjava.constants.WebConstants.*;

@Controller
public class RootController {

    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    @GetMapping("/")
    public String root() {
        log.info("root");
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        log.info(USERS_PARAM);
        model.addAttribute(USERS_PARAM, userService.getAll());
        return USERS_PARAM;
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        log.info("setUser {}", userId);
        SecurityUtil.setAuthUserId(userId);
        return REDIRECT_MEALS;
    }

    @GetMapping("/meals")
    public String getMeals(Model model) {
        model.addAttribute(
                MEALS_PARAM,
                MealsUtil.getTos(
                        mealService.getAll(SecurityUtil.authUserId()),
                        SecurityUtil.authUserCaloriesPerDay()
                ));
        return MEALS_PARAM;
    }
}