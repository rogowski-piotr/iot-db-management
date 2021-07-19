-- MEASUREMENTS TYPES
INSERT INTO measurments_type (id, type) VALUES ((SELECT nextval ('measurement_type_id_sequence')), 'TEMPERATURE_AND_HUMIDITY');
INSERT INTO measurments_type (id, type) VALUES ((SELECT nextval ('measurement_type_id_sequence')), 'TEMPERATURE');
INSERT INTO measurments_type (id, type) VALUES ((SELECT nextval ('measurement_type_id_sequence')), 'HUMIDITY');
INSERT INTO measurments_type (id, type) VALUES ((SELECT nextval ('measurement_type_id_sequence')), 'SOIL_MOISTURE');

-- PLACES
INSERT INTO places (id, description) VALUES ((SELECT nextval ('place_id_sequence')), 'room_1');
INSERT INTO places (id, description) VALUES ((SELECT nextval ('place_id_sequence')), 'room_2');

-- SENSORS
INSERT INTO sensors (id, active, measurement_frequency, socket, measurement_type_id, actual_position)
VALUES ((SELECT nextval ('sensor_id_sequence')), true, 'ONCE_PER_MINUTE', '192.168.0.19:50007', 4, 1);
INSERT INTO sensors (id, active, measurement_frequency, socket, measurement_type_id, actual_position)
VALUES ((SELECT nextval ('sensor_id_sequence')), true, 'ONCE_PER_MINUTE', '192.168.0.20:50007', 1, 2);