-- MEASUREMENTS TYPES
INSERT INTO measurments_type (id, type) VALUES (1, 'TEMPERATURE_AND_HUMIDITY');
INSERT INTO measurments_type (id, type) VALUES (2, 'TEMPERATURE');
INSERT INTO measurments_type (id, type) VALUES (3, 'HUMIDITY');


-- SENSORS
INSERT INTO sensors (id, active, measurement_frequency, socket, measurement_type_id) VALUES (1, true, 'ONCE_PER_MINUTE', '192.168.0.19:50007', 1);