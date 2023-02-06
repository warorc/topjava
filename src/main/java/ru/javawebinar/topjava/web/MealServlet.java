package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CrudItems;
import ru.javawebinar.topjava.dao.MemoryMeal;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private CrudItems<Meal> memoryMeal;

    @Override
    public void init() {
        memoryMeal = new MemoryMeal();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String forward = "";
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")) {
            log.debug("Delete procedure started");
            forward = "meals";
            int mealId = Integer.parseInt(request.getParameter("id"));
            memoryMeal.delete(mealId);
            response.sendRedirect(forward);
            log.debug("Delete successfully finished");
        } else if (action != null && action.equalsIgnoreCase("edit")) {
            log.debug("Redirect to Edit Meal page");
            forward = "/addMeal.jsp";
            int mealId = Integer.parseInt(request.getParameter("id"));
            Meal meal = memoryMeal.getById(mealId);
            request.setAttribute("meal", meal);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action != null && action.equalsIgnoreCase("add")) {
            log.debug("Redirect to Add Meal page");
            forward = "/addMeal.jsp";
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else {
            log.debug("Redirect to meal page");
            forward = "/meals.jsp";
            request.setAttribute(
                    "mealsTo",
                    MealsUtil.filteredByStreams((List<Meal>) memoryMeal.getAll(), null, null, 2000)
            );
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
            log.debug("Meal data showed in the UI table");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer id = null;
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            id = Integer.parseInt(request.getParameter("id"));
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), dtf);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(id, dateTime, description, calories);
        if (id == null) {
            memoryMeal.add(meal);
            log.debug("New meal was added");
        } else {
            memoryMeal.update(meal);
            log.debug("Meal with id: " + id + " was edited");
        }
        response.sendRedirect("meals");
    }
}
