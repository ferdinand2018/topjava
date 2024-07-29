package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    public static final String MEAL_FORM = "mealForm";
    public static final String REDIRECT_MEALS = "redirect:/meals";
    public static final String MEAL_PARAM = "meal";
    public static final String MEALS_PARAM = "meals";
    public static final String ID_PARAM = "id";

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(
                MEAL_PARAM,
                new Meal(
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                        "",
                        1000
                ));
        return MEAL_FORM;
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute(MEAL_PARAM, super.get(getId(request)));
        return MEAL_FORM;
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        if (request.getParameter(ID_PARAM).isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }

        return REDIRECT_MEALS;
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return REDIRECT_MEALS;
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        model.addAttribute(MEALS_PARAM, super.getBetween(
                startDate,
                startTime,
                endDate,
                endTime
        ));

        return MEALS_PARAM;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter(ID_PARAM));
        return Integer.parseInt(paramId);
    }
}
