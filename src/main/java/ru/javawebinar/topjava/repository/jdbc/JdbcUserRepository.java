package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Stream;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final ResultSetExtractor<List<User>> ROLE_RESULT_SET_EXTRACTOR = rs -> {
        Map<Integer, User> mapOfUsers = new LinkedHashMap<>();
        Map<Integer, Set<Role>> mapOfRoles = new HashMap<>();

        while (rs.next()) {
            User user = new User();
            if (!mapOfUsers.containsKey(rs.getInt("id"))) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setRegistered(rs.getDate("registered"));
                mapOfUsers.put(user.getId(), user);
            }
            String strRole = rs.getString("role");

            if (strRole != null) {
                mapOfRoles.merge(
                        rs.getInt("id"),
                        EnumSet.of(Role.valueOf(strRole)),
                        (oldSet, newSet) -> {
                            EnumSet<Role> resSet = EnumSet.noneOf(Role.class);
                            Stream.of(oldSet, newSet).forEach(resSet::addAll);
                            return resSet;
                        });
            }
        }

        List<User> resList = new LinkedList<>();
        mapOfUsers.forEach((k, v) -> {
            v.setRoles(mapOfRoles.get(k));
            resList.add(v);
        });
        return resList;
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Integer key;
        Set<ConstraintViolation<User>> constraintViolations = ValidationUtil.getValidator().validate(user);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            key = newKey.intValue();
            user.setId(key);
        } else {
            key = user.getId();
            if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password, 
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource) == 0) {
                return null;
            } else {
                jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.getId());
            }
        }
        Map<String, Object>[] batchOfInputs = user.getRoles().stream()
                .map((role) -> Map.of("userId", key, "role", role.name())).toArray(Map[]::new);
        namedParameterJdbcTemplate.batchUpdate("""
                INSERT INTO user_role ("user_id", "role") VALUES (:userId, :role)
                """, batchOfInputs);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("""
                SELECT * FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id WHERE id=? 
                """, ROLE_RESULT_SET_EXTRACTOR, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("""
                SELECT * FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id WHERE email=?
                """, ROLE_RESULT_SET_EXTRACTOR, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id ORDER BY name, email", ROLE_RESULT_SET_EXTRACTOR);
    }
}
