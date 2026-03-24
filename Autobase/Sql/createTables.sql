CREATE TABLE users(
id SERIAL PRIMARY KEY,
login VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
enabled BOOLEAN DEFAULT TRUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles(
id Serial PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE
);

CREATE table user_roles(
user_id integer NOT NULL,
role_id integer NOT NULL,
PRIMARY KEY(user_id,role_id),
FOREIGN KEY (user_id) REFERENCES users(id) ON delete cascade,
Foreign KEY (role_id) REFERENCES roles(id) on delete cascade
);

CREATE TABLE cars(
id Serial PRIMARY KEY,
model VARCHAR(100) NOT NULL,
mark VARCHAR(100) NOT NULL,
color VARCHAR(100) NOT NULL,      
year_of_release INTEGER NOT NULL,
state_number varchar(100) NOT NULL UNIQUE,
status VARCHAR(20) CHECK(status in ('available', 'in_use', 'repair')) default 'available',
mileage integer DEFAULT 0,
notes TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE drivers(
id Serial PRIMARY KEY,
user_id INTEGER UNIQUE REFERENCES users(id) ON delete cascade,
name VARCHAR(100) not null,
phone varchar(100) not null unique,
status varchar(20) CHECK(status in ('inactive','active')) DEFAULT 'active',
experience_years INTEGER NOT NULL default 0,    
notes TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE repair_requests(
id serial PRIMARY key,
driver_id INTEGER not null REFERENCES  drivers(id) on delete cascade,
car_id INTEGER NOT NULL references cars(id) on delete cascade,
description TEXT,
status VARCHAR(20) default 'submitted' CHECK(status in ('submitted','in_progress','complete','rejected')),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
completed_at TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE trips(
id serial PRIMARY key,
origin VARCHAR(100) not null,
destination varchar(100) not null,
status varchar(20) CHECK (status in('completed','cancelled','await','in_progress')) DEFAULT 'await',
driver_id INTEGER REFERENCES drivers(id) ON DELETE SET NULL,
car_id INTEGER REFERENCES cars(id) ON DELETE SET NULL,
started_at TIMESTAMP,
completed_at TIMESTAMP,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE trip_marks(
  id serial PRIMARY KEY,
    trip_id INTEGER UNIQUE REFERENCES trips(id) ON DELETE CASCADE,
    fuel_consumed DECIMAL(5,2),
    condition_notes TEXT,
    mark_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER tr_cars_updated_at
    BEFORE UPDATE ON cars
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();


CREATE TRIGGER tr_drivers_updated_at
    BEFORE UPDATE ON drivers
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();
	
CREATE TRIGGER tr_repair_requests_updated_at
    BEFORE UPDATE ON repair_requests
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER tr_trips_updated_at
    BEFORE UPDATE ON trips
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();



