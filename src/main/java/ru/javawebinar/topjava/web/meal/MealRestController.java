package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.formatter.CustomDateTimeFormat;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createdWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uRI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uRI).body(created);
    }

    @GetMapping("/filter")
    public List<MealTo> getBetween(
            @RequestParam(required = false) @CustomDateTimeFormat(type = CustomDateTimeFormat.Type.DATE) LocalDate startLocalDate,
            @RequestParam(required = false) @CustomDateTimeFormat(type = CustomDateTimeFormat.Type.TIME) LocalTime startLocalTime,
            @RequestParam(required = false) @CustomDateTimeFormat(type = CustomDateTimeFormat.Type.DATE) LocalDate endLocalDate,
            @RequestParam(required = false) @CustomDateTimeFormat(type = CustomDateTimeFormat.Type.TIME) LocalTime endLocalTime
    ) {
        return super.getBetween(
                startLocalDate,
                startLocalTime,
                endLocalDate,
                endLocalTime
        );
    }
}
