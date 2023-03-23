package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String get(Model model, HttpServletRequest request) {
        log.info("meals");
        model.addAttribute("meals", MealsUtil.getTos(
                getAll(),
                SecurityUtil.authUserCaloriesPerDay())
        );
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable int id) {
        log.info("delete meal {} for user {}", id, SecurityUtil.authUserId());
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String filter(Model model, HttpServletRequest request) {
        log.info("filter");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = getBetweenInclusive(startDate, endDate);
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

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("meal", get(id));
        log.info("redirect to update form for meal {}", id);
        return "mealForm";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("meal", new Meal());
        log.info("redirect to create form ");
        return "mealForm";
    }

    @PostMapping("/update/{id}")
    public String update(HttpServletRequest request, @PathVariable int id) {
        log.info("update meal {} for user {}", id, SecurityUtil.authUserId());
        update(new Meal(
                id,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")))
        );
        return "redirect:/meals";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest request) {
        log.info("create meal for user {}", SecurityUtil.authUserId());
        create(new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")))
        );
        return "redirect:/meals";
    }
}
