INSERT INTO TB_DEPARTMENT(name) VALUES ('Technology'), ('Human Resources'), ('Financial');

INSERT INTO TB_ROLE(authority) VALUES ('ADMIN'), ('CUSTOMER');

INSERT INTO TB_USER(name, email, password, is_active, created_at, updated_at, dep_id)
VALUES ('Alex Green', 'alex@gmail.com', '$2a$12$ku1SPVl46k7lYoYq.Y8Bau5WqpnQVcslitP0Maw.dqz/GITb.uYQ2', true, now(), now(), 1),
('Maria Brown', 'maria@gmail.com', '$2a$12$ku1SPVl46k7lYoYq.Y8Bau5WqpnQVcslitP0Maw.dqz/GITb.uYQ2', true, now(), now(), 2);

INSERT INTO TB_USER_ROLE(role_id, user_id) VALUES (1, 1), (2, 2);