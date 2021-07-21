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
INSERT INTO sensor_settings (id, acceptable_consecutive_failures, cycles_to_refresh_activity, request_timeout)
VALUES ((SELECT nextval ('sensor_settings_id_sequence')), 1, 2, 5000);


-- SENSORS
INSERT INTO sensors (id, active, measurement_frequency, socket, measurement_type_id, place_id, consecutive_failures, sensor_settings_id)
VALUES ((SELECT nextval ('sensor_id_sequence')), true, 'ONCE_PER_MINUTE', '192.168.0.19:50007', 4, 1, 0, 1);

INSERT INTO sensors (id, active, measurement_frequency, socket, measurement_type_id, place_id, consecutive_failures, sensor_settings_id)
VALUES ((SELECT nextval ('sensor_id_sequence')), true, 'ONCE_PER_MINUTE', '192.168.0.20:50007', 1, 2, 0, 1);