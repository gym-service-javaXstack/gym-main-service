INSERT INTO training_type (id, training_type_name) VALUES (1, 'Boxing') ON CONFLICT (id) DO NOTHING;
INSERT INTO training_type (id, training_type_name) VALUES (2, 'Swimming') ON CONFLICT (id) DO NOTHING;
INSERT INTO training_type (id, training_type_name) VALUES (3, 'Jumping') ON CONFLICT (id) DO NOTHING;
INSERT INTO training_type (id, training_type_name) VALUES (4, 'Test') ON CONFLICT (id) DO NOTHING;