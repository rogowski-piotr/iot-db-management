-- MEASUREMENTS TYPES
INSERT INTO measurments_type (id, type) VALUES (1, 'TEMPERATURE_AND_HUMIDITY');
INSERT INTO measurments_type (id, type) VALUES (2, 'TEMPERATURE');
INSERT INTO measurments_type (id, type) VALUES (3, 'HUMIDITY');

-- PLACES
INSERT INTO places (id, description) VALUES (1, 'room_1');
INSERT INTO places (id, description) VALUES (2, 'room_2');

-- SENSORS
INSERT INTO sensors (id, active, measurement_frequency, socket, measurement_type_id, actual_position)
VALUES (1, true, 'ONCE_PER_MINUTE', '192.168.0.19:50007', 1, 1);
INSERT INTO sensors (id, active, measurement_frequency, socket, measurement_type_id, actual_position)
VALUES (2, false, 'ONCE_PER_MINUTE', '192.168.0.20:50007', 1, 2);