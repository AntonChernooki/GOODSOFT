INSERT INTO roles (name) VALUES ('ROLE_DISPATCHER');
INSERT INTO roles (name) VALUES ('ROLE_DRIVER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (login, password, enabled) VALUES
    ('admin', '$2a$12$No9TvuB69ON9AZ6v.DntCeNngdrOVOiuoiAmP3uSuIJe2.nbUTq.2', TRUE), 
    ('dispatcher1', '$$2a$12$No9TvuB69ON9AZ6v.DntCeNngdrOVOiuoiAmP3uSuIJe2.nbUTq.2', TRUE),
    ('dispatcher2', '$$2a$12$No9TvuB69ON9AZ6v.DntCeNngdrOVOiuoiAmP3uSuIJe2.nbUTq.2', TRUE),
    ('driver1', '$2a$12$No9TvuB69ON9AZ6v.DntCeNngdrOVOiuoiAmP3uSuIJe2.nbUTq.2', TRUE),
    ('driver2', '$2a$12$No9TvuB69ON9AZ6v.DntCeNngdrOVOiuoiAmP3uSuIJe2.nbUTq.2', TRUE),
    ('driver3', '$2a$12$No9TvuB69ON9AZ6v.DntCeNngdrOVOiuoiAmP3uSuIJe2.nbUTq.2', TRUE),
    ('driver_inactive', '$2a$12$No9TvuB69ON9AZ6v.DntCeNngdrOVOiuoiAmP3uSuIJe2.nbUTq.2', FALSE)


INSERT INTO user_roles (user_id, role_id)
SELECT users.id, roles.id
FROM users, roles
WHERE (users.login = 'admin' AND roles.name = 'ROLE_ADMIN')
   OR (users.login = 'dispatcher1' AND roles.name = 'ROLE_DISPATCHER')
   OR (users.login = 'dispatcher2' AND roles.name = 'ROLE_DISPATCHER')
   OR (users.login = 'driver1' AND roles.name = 'ROLE_DRIVER')
   OR (users.login = 'driver2' AND roles.name = 'ROLE_DRIVER')
   OR (users.login = 'driver3' AND roles.name = 'ROLE_DRIVER')
   OR (users.login = 'driver_inactive' AND roles.name = 'ROLE_DRIVER')



INSERT INTO cars (model, mark, color, year_of_release, state_number, status, mileage, notes) VALUES
    ('Camry', 'Toyota', 'Белый', 2020, 'А123ВВ77', 'available', 45000, 'Отличное состояние'),
    ('Focus', 'Ford', 'Синий', 2021, 'В456СС99', 'available', 32000, 'Требуется замена масла'),
    ('Octavia', 'Skoda', 'Серый', 2019, 'Е789КК77', 'repair', 78000, 'Проблемы с двигателем'),
    ('X5', 'BMW', 'Черный', 2022, 'М101РР77', 'available', 15000, ''),
    ('Golf', 'Volkswagen', 'Красный', 2020, 'О202НН77', 'in_use', 52000, 'В рейсе'),
    ('Rio', 'Kia', 'Серебристый', 2023, 'С303АА77', 'available', 5000, 'Новый автомобиль')


INSERT INTO drivers (user_id, name, phone, status, experience_years, notes) VALUES
    (4, 'Иван Петров', '+79161234567', 'active', 5, 'Опытный водитель'),
    (5, 'Сергей Сидоров', '+79162345678', 'active', 3, 'Нет нарушений'),
    (6, 'Алексей Иванов', '+79163456789', 'active', 8, 'Лучший сотрудник месяца'),
    (7, 'Дмитрий Смирнов', '+79164567890', 'inactive', 2, 'Отстранён за нарушения');


INSERT INTO trips (origin, destination, status, driver_id, car_id, started_at, completed_at) VALUES
    ('Москва', 'Санкт-Петербург', 'completed', 1, 1, '2025-01-15 08:00:00', '2025-01-15 14:30:00'),
    ('Москва', 'Казань', 'in_progress', 2, 2, '2025-03-20 10:00:00', NULL),
    ('Санкт-Петербург', 'Москва', 'await', 3, 6, NULL, NULL),
    ('Москва', 'Нижний Новгород', 'cancelled', 1, 1, '2025-02-10 09:00:00', NULL),
    ('Москва', 'Воронеж', 'completed', 2, 2, '2025-03-01 07:30:00', '2025-03-01 12:15:00');



INSERT INTO repair_requests (driver_id, car_id, description, status, created_at, completed_at) VALUES
    (1, 1, 'Стук в подвеске', 'complete', '2025-01-20 10:00:00', '2025-01-22 16:00:00'),
    (3, 3, 'Не заводится', 'in_progress', '2025-03-18 09:00:00', NULL),
    (2, 5, 'Прокол колеса', 'submitted', '2025-03-21 14:30:00', NULL),
    (4, 4, 'Проблемы с тормозами', 'rejected', '2025-02-25 11:00:00', '2025-02-26 09:00:00');

	
INSERT INTO trip_marks (trip_id, fuel_consumed, condition_notes) VALUES
    (1, 45.5, 'Машина в порядке, требуется замена масла'),
    (5, 38.2, 'Всё отлично, расход в норме');





