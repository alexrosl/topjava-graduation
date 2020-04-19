DELETE FROM meals;
DELETE FROM users;
DELETE FROM user_roles;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
    ('user', 'email@mail.ru', 'password'),
    ('admin', 'admin@mail.ru', 'admin');

INSERT INTO user_roles (user_id, role) VALUES
    (100000, 'ROLE_USER'),
    (100001, 'ROLE_ADMIN');

INSERT INTO meals (user_id, date_time, description, calories) VALUES
    (100000, '2015-05-30 10:00:00', 'Завтрак', 500),
    (100000, '2015-05-30 13:00:00', 'Обед', 1000),
    (100000, '2015-05-30 20:00:00', 'Ужин', 500),
    (100000, '2015-05-31 10:00:00', 'Завтрак', 500),
    (100000, '2015-05-31 13:00:00', 'Обед', 1000),
    (100000, '2015-05-31 20:00:00', 'Ужин', 510),
    (100001, '2015-06-01 14:00:00', 'Админ ланч', 510),
    (100001, '2015-06-01 21:00:00', 'Админ ужин', 1500)
