package ru.javawebinar.topjava.service.jpa;

import org.junit.AfterClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static org.slf4j.LoggerFactory.getLogger;

@ActiveProfiles({Profiles.JPA})
public class JpaUserServiceTest extends UserServiceTest {
}
