INSERT INTO role (description, label) VALUES ('ADMIN', 'Administrador');
INSERT INTO role (description, label) VALUES ('FLEET_MANAGER', 'Gestor de frota');
INSERT INTO role (description, label) VALUES ('DRIVER', 'Motorista');
INSERT INTO role (description, label) VALUES ('AGENT', 'Agente');

INSERT INTO users (name, registration, password, status, role_id) VALUES ('Admin', '1234', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE', 1);
INSERT INTO users (name, registration, password, status, role_id) VALUES ('Luan', '4321', '$2a$10$muqKX94KrOwFcmVYXIIsi..Hlr/RgXqkjy2eUsUZ/fV7ljKnzIwvW', 'ACTIVE', 3);

INSERT INTO vehicle (make, model, license_plate, type, capacity, mileage, status) VALUES ('VolksWagem', 'Gol 1.0', 'OLM9220', 'HATCH', 5, 12948, 'AVAILABLE');