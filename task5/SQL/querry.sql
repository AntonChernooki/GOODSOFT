--1 задание

--создал схему 
CREATE SCHEMA "authorization";

--создаю таблицу пользователи 
CREATE TABLE "authorization".users(
--первичный ключ
id SERIAL PRIMARY KEY,
login VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
name VARCHAR(100) NOT NULL,
birth_date DATE NULL
);

--создаю таблицу роли
CREATE TABLE "authorization".roles(
id 	SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE
)

--2 задание
--изменяю таблицу пользователей
ALTER TABLE "authorization".users
ADD COLUMN age INT NOT NULL CHECK(age>18),
ADD COLUMN salary MONEY NULL;


--создал индекс на столбец name
CREATE INDEX index_user_name ON "authorization".users(name);

--добавил поле в таблицу пользователей(для внешнего ключа)
ALTER TABLE "authorization".users
ADD COLUMN role_id INT;

--добавил свять с таблицей ролей
ALTER TABLE "authorization".users 
ADD FOREIGN KEY (role_id) REFERENCES "authorization".roles(id);

--3 задание

--заполняю данными таблицы пользователи и роли
INSERT INTO "authorization".roles (name)
values 
('ADMIN'),
('USER'),
('MANAGER'),
('GUEST'),
('SUPERUSER');

INSERT INTO "authorization".users (login, password, name, birth_date, age, salary, role_id)
values
('ivanov', 'pass123', 'Иван', '1990-05-15', 34, 2500.00, 1),
    ('petrov', 'qwerty', 'Петр', '1985-08-20', 39, 3200.00, 2),
    ('sidorova', 'secret', 'Мария', '1995-12-01', 29, 2800.00, 3),
    ('smirnov', '123456', 'Алексей', NULL, 22, NULL, 4),
    ('kuznetsova', 'password', 'Елена', '2000-03-10', 24, 1500.00, 5),
    ('popov', 'passpass', 'Дмитрий', '1988-07-07', 36, 4000.00, 2),
    ('vasileva', 'vasya', 'Анна', '1992-11-11', 32, 2100.00, 3),
    ('mikhailov', 'mike', 'Михаил', '1979-01-01', 45, 5000.00, 1),
    ('fedorov', 'fedya', 'Федор', '1998-09-09', 26, 1800.00, NULL),
    ('nikolaeva', 'nikki', 'Надежда', '1993-04-04', 31, 2700.00, 5),
    ('alekseev', 'alex', 'Александр', NULL, 19, 1000.00, 4),
    ('egorova', 'egor', 'Екатерина', '1982-06-06', 42, 3500.00, 2),
    ('andreev', 'andrey', 'Андрей', '1991-10-10', 33, 2900.00, 3),
    ('sergeeva', 'sergey', 'Светлана', '1996-02-02', 28, 2400.00, 1),
    ('pavlov', 'pasha', 'Павел', '1987-12-12', 37, 3100.00, NULL);



--4 задание (select запросы)

--выбрать всех пользователей
SELECT * from "authorization".users;

--выбрать всех пользователей  с непустой датой рождения
SELECT * from "authorization".users
WHERE "authorization".users.birth_date is not null




--выбрать всех пользователей  с датой рождения в заданных пределах (от и до)
SELECT * from "authorization".users
WHERE "authorization".users.birth_date BETWEEN '1995-01-01' AND '2000-01-01';


--выбрать количество пользователей  с одинаковым возрастом (на выходе 2 колонки - возраст и количество)
SELECT "authorization".users.age, COUNT (*) --агрегатная функция для подсчета количества
from "authorization".users 
--группировка по возрасту
GROUP BY age;





--к предыдущему добавить ограничение по количеству только больше 1 и отсортировать по количеству по убыванию
SELECT "authorization".users.age, COUNT (*) as count -- дал название для сортировки по убыванию
from "authorization".users 
GROUP BY age
HAVING  COUNT (*)>1
ORDER by count DESC;

--выбрать только пользователей с ролями (роли так же вывести)
SELECT "authorization".users.name, "authorization".roles.name from "authorization".users
--пересечение таблиц пользователи и роли
join "authorization".roles on "authorization".users.role_id="authorization".roles.id


--выбрать всех пользователей и их роли
SELECT "authorization".users.name, "authorization".roles.name from "authorization".users
--к строчкам из левой таблицы добовляю строчки из правой таблицы 
left join "authorization".roles on "authorization".users.role_id="authorization".roles.id;



--ограничить  вывод предыдущего запроса 5ю записями
SELECT "authorization".users.name, "authorization".roles.name from "authorization".users
left join "authorization".roles on "authorization".users.role_id="authorization".roles.id
--ограничение вывода
limit 5 ;

--ограничить  вывод предыдущего  начиная с 3й
SELECT "authorization".users.name, "authorization".roles.name from "authorization".users
left join "authorization".roles on "authorization".users.role_id="authorization".roles.id
--смещение вывода на 2 элемента
offset 2;



--5 задание изменить таблицу пользователей


-- изменить колонку зарплата  на непустое со значением по умолчанию - 1000 (старые значения сохранить)

UPDATE "authorization".users SET salary = 1000 WHERE salary IS NULL;

ALTER TABLE "authorization".users 
ALTER COLUMN salary SET NOT NULL,
--присваиваю значение по умолчанию
ALTER  COLUMN salary SET default 1000 ;



--посчитать среднюю зарплату пользователей с возрастом меньше 25 и отдельно 
--(использовать объединение запросов) больше 25

--приведение типов тк функиция не работает с типом money
SELECT  AVG(salary::numeric)
from "authorization".users 
WHERE age<25;

SELECT  AVG(salary::numeric)
from "authorization".users 
WHERE age<25
UNION ALL
SELECT  AVG(salary::numeric)
from "authorization".users 
WHERE age>25;


--6 задание Обновление


--Всем у кого зарплата меньше 3000 добавить к зарплате 20%
update "authorization".users 
set salary =salary*1.2
WHERE salary::numeric < 3000;

--Тем у кого имя начинается на заданную букву роль установить на "Менеджер" (если такой нет любую из имеющихся) 

update "authorization".users 
set role_id=(select id from "authorization".roles where name='MANAGER' )
WHERE name like 'А%';

--7 задание перевод к схеме многие ко многим
CREATE TABLE "authorization".user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES "authorization".users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES "authorization".roles(id) ON DELETE CASCADE
);
--перенос ролей в новую таблицу 
INSERT INTO "authorization".user_roles (user_id, role_id)
SELECT id, role_id
FROM "authorization".users
WHERE role_id IS NOT NULL;

--удаление связи с таблицей roles
alter TABLE "authorization".users drop COLUMN role_id;

-- 8 задание

--Назначить заданному пользователю какие либо 3 роли которые у него отсутствуют
INSERT INTO "authorization".user_roles (user_id, role_id)
select 1, id
from "authorization".roles
where id not in(select role_id FROM "authorization".user_roles WHERE user_id = 1)
LIMIT 3;

--выбрать всех пользователей и их роли
select users.name, roles.name
from "authorization".users
LEFT join "authorization".user_roles on users.id=user_roles.user_id
LEFT join "authorization".roles on user_roles.role_id=roles.id


--выбрать только пользователей с ролями
select users.name, roles.name
from "authorization".users
 join "authorization".user_roles on users.id=user_roles.user_id
 join "authorization".roles on user_roles.role_id=roles.id


select name from "authorization".users;
--Удалить выбранного пользователя (у которого есть роли)
DELETE FROM "authorization".users
WHERE id = 2
  AND EXISTS (SELECT 2 FROM "authorization".user_roles WHERE user_id = 1);



--9. Задание 
--выбрать всех пользователей и все роли независимо от привязок одним запросом
select users.name, roles.name
from "authorization".users
cross join "authorization".roles 



--выбрать всех пользователей и все роли для которых соответственно нету привязок ролей и пользователей  

-- Пользователи без ролей
SELECT u.id, u.name
FROM "authorization".users u
LEFT JOIN "authorization".user_roles ur ON u.id = ur.user_id
WHERE ur.user_id IS NULL

UNION ALL

-- Роли без пользователей
SELECT r.id, r.name
FROM "authorization".roles r
LEFT JOIN "authorization".user_roles ur ON r.id = ur.role_id
WHERE ur.role_id IS NULL;
