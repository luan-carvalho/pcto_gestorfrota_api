INSERT INTO users (name, registration, password, status) VALUES ('Admin', '1234', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE');

INSERT INTO user_roles (user_id, role) VALUES (1, 'FLEET_MANAGER');
INSERT INTO user_roles (user_id, role) VALUES (1, 'REQUESTER');
INSERT INTO user_roles (user_id, role) VALUES (1, 'DRIVER');
INSERT INTO user_roles (user_id, role) VALUES (1, 'ADMIN');

INSERT INTO users (name, registration, password, status) VALUES ('Luam', '4321', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE');

INSERT INTO user_roles (user_id, role) VALUES (2, 'DRIVER');

INSERT INTO vehicle (make, model, license_plate, type, capacity, current_mileage, status) VALUES ('VolksWagem', 'Gol 1.0', 'OLM9220', 'HATCH', 5, 12948, 'AVAILABLE');