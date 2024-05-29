INSERT INTO training_type (id, training_type_name) VALUES (1, 'Test');
INSERT INTO training_type (id, training_type_name) VALUES (2, 'Boxing');

-- Creating users password = "test"
INSERT INTO usr (id, first_name, last_name, user_name, password, is_active) VALUES (1, 'John', 'Trainer', 'John.Trainer', '$2a$12$8PlfeAePckMQHKunPL7A/uGTObIZ7/1TNE19yTonkf/edsOaifNaq', true);
INSERT INTO usr (id, first_name, last_name, user_name, password, is_active) VALUES (2, 'Jane', 'Trainee', 'Jane.Trainee', '$2a$12$8PlfeAePckMQHKunPL7A/uGTObIZ7/1TNE19yTonkf/edsOaifNaq', true);
INSERT INTO usr (id, first_name, last_name, user_name, password, is_active) VALUES (3, 'Alice', 'Trainer', 'Alice.Trainer', '$2a$12$8PlfeAePckMQHKunPL7A/uGTObIZ7/1TNE19yTonkf/edsOaifNaq', true);
INSERT INTO usr (id, first_name, last_name, user_name, password, is_active) VALUES (4, 'Bob', 'Trainee', 'Bob.Trainee', '$2a$12$8PlfeAePckMQHKunPL7A/uGTObIZ7/1TNE19yTonkf/edsOaifNaq', true);
INSERT INTO usr (id, first_name, last_name, user_name, password, is_active) VALUES (5, 'Bob', 'Trainee', 'Bob.Trainee1', '$2a$12$8PlfeAePckMQHKunPL7A/uGTObIZ7/1TNE19yTonkf/edsOaifNaq', true);
INSERT INTO usr (id, first_name, last_name, user_name, password, is_active) VALUES (6, 'John', 'Trainer', 'John.Trainer1', '$2a$12$8PlfeAePckMQHKunPL7A/uGTObIZ7/1TNE19yTonkf/edsOaifNaq', true);

ALTER SEQUENCE usr_seq RESTART WITH 56;

-- Creating trainers
INSERT INTO trainer (id, specialization_id, usr_id) VALUES (1, 1, 1);
INSERT INTO trainer (id, specialization_id, usr_id) VALUES (2, 1, 3);
INSERT INTO trainer (id, specialization_id, usr_id) VALUES (3, 2, 6);

ALTER SEQUENCE trainer_seq RESTART WITH 55;

-- Creating trainee
INSERT INTO trainee (id, address, date_of_birth, usr_id) VALUES (1, '123 Main St', '2000-01-01', 2);
INSERT INTO trainee (id, address, date_of_birth, usr_id) VALUES (2, '456 Elm St', '2000-02-02', 4);
INSERT INTO trainee (id, address, date_of_birth, usr_id) VALUES (3, '257 Apple St', '1990-02-02', 5);

ALTER SEQUENCE trainee_seq RESTART WITH 54;

-- Link relations
INSERT INTO trainee_trainer (trainee_id, trainer_id) VALUES (1, 1);
INSERT INTO trainee_trainer (trainee_id, trainer_id) VALUES (2, 2);


-- Creating trainings
INSERT INTO training (id, training_name, training_type_id, training_date, training_duration, trainee_id, trainer_id) VALUES (1, 'Training 1', 1, '2022-01-01', 60, 1, 1);
INSERT INTO training (id, training_name, training_type_id, training_date, training_duration, trainee_id, trainer_id) VALUES (2, 'Training 2', 1, '2022-01-01', 80, 2, 1);
INSERT INTO training (id, training_name, training_type_id, training_date, training_duration, trainee_id, trainer_id) VALUES (3, 'Training 3', 1, '2022-01-01', 97, 3, 1);
INSERT INTO training (id, training_name, training_type_id, training_date, training_duration, trainee_id, trainer_id) VALUES (4, 'Training 4', 1, '2022-01-02', 90, 2, 2);

ALTER SEQUENCE training_seq RESTART WITH 56;