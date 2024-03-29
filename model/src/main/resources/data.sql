-- ROLES
INSERT INTO roles (id, name)
VALUES ((SELECT nextval ('roles_id_sequence')), 'ADMIN');

INSERT INTO roles (id, name)
VALUES ((SELECT nextval ('roles_id_sequence')), 'USER');


-- USERS
INSERT INTO users (id, username, email, password, role_id)
VALUES ((SELECT nextval ('users_id_sequence')), 'admin', 'admin@example.com', '21232f297a57a5a743894a0e4a801fc3', 1); --password: admin

INSERT INTO users (id, username, email, password, role_id)
VALUES ((SELECT nextval ('users_id_sequence')), 'Grzegorz Brzęczyszczykiewicz', 'grzegorz@example.com', '5ebe2294ecd0e0f08eab7690d2a6ee69', 2); -- password: secret


-- MEASUREMENTS TYPES
INSERT INTO measurment_types (id, type)
VALUES ((SELECT nextval ('measurement_type_id_sequence')), 'TEMPERATURE_AND_HUMIDITY');

INSERT INTO measurment_types (id, type)
VALUES ((SELECT nextval ('measurement_type_id_sequence')), 'TEMPERATURE');

INSERT INTO measurment_types (id, type)
VALUES ((SELECT nextval ('measurement_type_id_sequence')), 'HUMIDITY');

INSERT INTO measurment_types (id, type)
VALUES ((SELECT nextval ('measurement_type_id_sequence')), 'SOIL_MOISTURE');


-- PLACES
INSERT INTO places (id, description)
VALUES ((SELECT nextval ('place_id_sequence')), 'room_1');

INSERT INTO places (id, description)
VALUES ((SELECT nextval ('place_id_sequence')), 'room_2');


-- SENSOR_SETTINGS
INSERT INTO sensor_settings (id, name, acceptable_consecutive_failures, cycles_to_refresh_activity, request_timeout)
VALUES ((SELECT nextval ('sensor_settings_id_sequence')), 'default', 2, 1, 5000);


-- SENSORS
INSERT INTO sensors (id, name, active, measurement_frequency, socket, place_id, measurement_type_id, sensor_settings_id)
VALUES ((SELECT nextval ('sensor_id_sequence')), 'sensor 1', true, 'ONCE_PER_MINUTE', '192.168.0.19:50007', 1, 4, 1);

INSERT INTO sensors (id, name, active, measurement_frequency, socket, place_id, measurement_type_id, sensor_settings_id)
VALUES ((SELECT nextval ('sensor_id_sequence')), 'sensor 2', true, 'ONCE_PER_MINUTE', '192.168.0.20:50007', 2, 1, 1);


-- MEASUREMENTS
INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-1 * (interval '1 minutes'))))), 4, 1, 1, (SELECT random() * 15 + 10));
INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-2 * (interval '1 minutes'))))), 4, 1, 1, (SELECT random() * 15 + 10));
INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-3 * (interval '1 minutes'))))), 4, 1, 1, (SELECT random() * 15 + 10));

INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-1 * (interval '1 minutes'))))), 3, 1, 2, (SELECT random() * 15 + 10));
INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-1 * (interval '1 minutes'))))), 2, 1, 2, (SELECT random() * 15 + 10));

INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-2 * (interval '1 minutes'))))), 3, 1, 2, (SELECT random() * 15 + 10));
INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-2 * (interval '1 minutes'))))), 2, 1, 2, (SELECT random() * 15 + 10));

INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-3 * (interval '1 minutes'))))), 3, 1, 2, (SELECT random() * 15 + 10));
INSERT INTO sensors.measurments (id, date, measurement_type_id, place_id, sensor_id, value) values ((SELECT uuid_in(md5(random()::text || clock_timestamp()::text)::cstring)), (select date_trunc('second', (SELECT NOW() + (-3 * (interval '1 minutes'))))), 2, 1, 2, (SELECT random() * 15 + 10));