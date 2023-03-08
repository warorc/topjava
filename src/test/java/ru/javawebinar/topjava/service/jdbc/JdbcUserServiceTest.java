package ru.javawebinar.topjava.service.jdbc;

import org.junit.AfterClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static org.slf4j.LoggerFactory.getLogger;

@ActiveProfiles({Profiles.JDBC})
public class JdbcUserServiceTest extends UserServiceTest {
}
