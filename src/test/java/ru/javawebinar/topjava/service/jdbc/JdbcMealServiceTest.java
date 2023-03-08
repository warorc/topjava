package ru.javawebinar.topjava.service.jdbc;

import org.junit.AfterClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static org.slf4j.LoggerFactory.getLogger;

@ActiveProfiles({Profiles.JDBC})
public class JdbcMealServiceTest extends MealServiceTest {
}
