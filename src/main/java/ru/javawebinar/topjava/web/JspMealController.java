package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    MealService service;

    @GetMapping("/meals")
    public String getMeals(Model model, HttpServletRequest request) {
        log.info("meals");
        model.addAttribute("meals", MealsUtil.getTos(
                service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay())
        );
        return "meals";
    }

    @GetMapping("/meals/delete/{id}")
    public String deleteMeal(@PathVariable int id) {
        log.info("delete meal {} for user {}", id, SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/meals")
    public String filterMeal(Model model, HttpServletRequest request) {
        log.info("filter");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
        model.addAttribute(
                "meals",
                MealsUtil.getFilteredTos(
                        mealsDateFiltered,
                        SecurityUtil.authUserCaloriesPerDay(),
                        startTime,
                        endTime
                )
        );
        return "meals";
    }

    @GetMapping("/meals/update/{id}")
    public String updateMealRedirect(@PathVariable int id, Model model) {
        log.info("redirect to update form for meal {}", id);
        model.addAttribute("meal", service.get(id, SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/meals/create")
    public String createMealRedirect(Model model) {
        log.info("redirect to create form ");
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }

    @PostMapping("/meals/update/{id}")
    public String updateMeal(HttpServletRequest request, @PathVariable int id) {
        log.info("update meal {} for user {}", id, SecurityUtil.authUserId());
        service.update( new Meal(
                id,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))),
                SecurityUtil.authUserId()
        );
        return "redirect:/meals";
    }

    @PostMapping("/meals/create")
    public String createMeal(HttpServletRequest request) {
        log.info("create meal for user {}", SecurityUtil.authUserId());
        service.create( new Meal(
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))),
                SecurityUtil.authUserId()
        );
        return "redirect:/meals";
    }


}
