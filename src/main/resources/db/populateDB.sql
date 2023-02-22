DELETE
FROM meals;
DELETE
FROM user_role;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2023-02-02 10:00:00', 'Завтрак User', 500),
       (100000, '2023-02-02 13:00:00', 'Обед User', 1000),
       (100000, '2023-02-02 20:00:00', 'Ужин User', 500),
       (100000, '2023-02-03 00:00:00', 'Еда на граничное значение User', 100),
       (100000, '2023-02-03 10:00:00', 'Завтрак User', 1000),
       (100000, '2023-02-03 13:00:00', 'Обед User', 500),
       (100000, '2023-02-03 20:00:00', 'Ужин User', 410),
       (100001, '2023-02-02 10:00:00', 'Завтрак Admin', 500),
       (100001, '2023-02-02 13:00:00', 'Обед Admin', 1000),
       (100001, '2023-02-02 20:00:00', 'Ужин Admin', 500),
       (100001, '2023-02-03 00:00:00', 'Еда на граничное значение Admin', 100),
       (100001, '2023-02-03 10:00:00', 'Завтрак Admin', 1000),
       (100001, '2023-02-03 13:00:00', 'Обед Admin', 500),
       (100001, '2023-02-03 20:00:00', 'Ужин Admin', 410);