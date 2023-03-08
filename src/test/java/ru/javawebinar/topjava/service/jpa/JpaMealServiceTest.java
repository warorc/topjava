package ru.javawebinar.topjava.service.jpa;

import org.junit.AfterClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static org.slf4j.LoggerFactory.getLogger;

@ActiveProfiles({Profiles.JPA})
public class JpaMealServiceTest extends MealServiceTest {

}
