package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CrudItems;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private final static Logger log = getLogger(MealServlet.class);
    private final CrudItems<Meal> mealDao;

    public MealServlet() {
        super();
        mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("Redirect to meals page");
        String forward = "";
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")) {
            forward = "meals";
            int mealId = Integer.parseInt(request.getParameter("id"));
            mealDao.deleteItem(mealId);
            response.sendRedirect(forward);
        } else if (action != null && action.equalsIgnoreCase("edit")) {
            forward = "/addMeal.jsp";
            int mealId = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDao.getItemById(mealId);
            request.setAttribute("meal", meal);
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else {
            forward = "/meals.jsp";

            request.setAttribute(
                    "mealsTo",
                    MealsUtil.filteredByStreams(mealDao.getAllItems(), null, null, 2000)
            );
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
        log.debug("Show all meals in the UI table");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = null;
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            id = Integer.parseInt(request.getParameter("id"));
        }
        Date dateOfEating = null;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            dateOfEating = (Date) formatter.parse(request.getParameter("dateTime"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        LocalDateTime dateTime = dateOfEating.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(id, dateTime, description, calories);
        if (id == null) {
            mealDao.addItem(meal);
        } else {
            mealDao.editItem(meal);
        }
        doGet(request, response);
    }
}
