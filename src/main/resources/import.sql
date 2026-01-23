INSERT INTO roles (description) VALUES ('ADMIN');
INSERT INTO roles (description) VALUES ('FLEET_MANAGER');
INSERT INTO roles (description) VALUES ('DRIVER');
INSERT INTO roles (description) VALUES ('AGENT');

INSERT INTO users (name, registration, password, status, role_id) VALUES ('Admin', '1234', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE', 1);

INSERT INTO vehicle (make, model, license_plate, type, capacity, mileage, status) VALUES ('VolksWagem', 'Gol 1.0', 'OLM9220', 'HATCH', 5, 12948, 'AVAILABLE');