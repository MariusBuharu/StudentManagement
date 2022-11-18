------------------------role---------------------------------------------
INSERT INTO role(id, role_name) VALUES(1, 'STUDENT') ON CONFLICT DO NOTHING;
INSERT INTO role(id, role_name) VALUES(2, 'VISITOR') ON CONFLICT DO NOTHING;
INSERT INTO role(id, role_name) VALUES(3, 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO role(id, role_name) VALUES(4, 'DIRECTOR') ON CONFLICT DO NOTHING;

INSERT INTO address_type(id, type)VALUES (1, 'Institution address');
INSERT INTO address_type(id, type)VALUES (2, 'User address');
-------------------------user addresses------------------------------------
INSERT INTO address(address_type_id, country, city, address_line_one) VALUES (2,'Romania', 'Bucharest', 'Calea Grivitei, Street no 1') ON CONFLICT DO NOTHING;
INSERT INTO address(address_type_id, country, city, address_line_one) VALUES (2,'Romania', 'Bucharest', 'Piata Victoriei') ON CONFLICT DO NOTHING;
INSERT INTO address(address_type_id, country, city, address_line_one) VALUES (2,'Romania', 'Gorj', '..............') ON CONFLICT DO NOTHING;
-------------------------app_users-----------------------------------------
--All users raw password is: password123
INSERT INTO app_user(role_id, address_id, first_name, last_name, username, email, password, dob )VALUES (1, 1, 'John', 'Smith', 'John85', 'john.smith@gmail.test', '$2a$10$WTaxKlausNUrBivU6v.vFO.h69oF0Cufs2O5Y6Y8R4U2WOoZwJ4l2', '1985-05-23') ON CONFLICT DO NOTHING;
INSERT INTO app_user(role_id, address_id, first_name, last_name, username, email, password, dob )VALUES (2, 3, 'Tomas', 'Winchester', 'Tomas90', 'tomas90@gmail.test', '$2a$10$WTaxKlausNUrBivU6v.vFO.h69oF0Cufs2O5Y6Y8R4U2WOoZwJ4l2', '1990-10-23') ON CONFLICT DO NOTHING;
INSERT INTO app_user(role_id, address_id, first_name, last_name, username, email, password, dob )VALUES (3, 2, 'Anne', 'Smith', 'Anne88', 'anne.smith@gmail.test', '$2a$10$WTaxKlausNUrBivU6v.vFO.h69oF0Cufs2O5Y6Y8R4U2WOoZwJ4l2', '1988-01-24') ON CONFLICT DO NOTHING;
INSERT INTO app_user(role_id, address_id, first_name, last_name, username, email, password, dob )VALUES (4, null, 'Ben', 'Williams', 'Ben04', 'ben.benny@gmail.test', '$2a$10$WTaxKlausNUrBivU6v.vFO.h69oF0Cufs2O5Y6Y8R4U2WOoZwJ4l2', '2004-07-23') ON CONFLICT DO NOTHING;
-------------------------institution addresses-------------------------------
INSERT INTO address(address_type_id, country, city, address_line_one) VALUES (1,'Romania', 'Bucharest', '..........') ON CONFLICT DO NOTHING;
INSERT INTO address(address_type_id, country, city, address_line_one) VALUES (1,'Romania', 'Gorj', '..............') ON CONFLICT DO NOTHING;
-------------------------institution-----------------------------------------
INSERT INTO institution(address_id, institution_name, description, founded_date)VALUES(4,'Software Developer Factory', 'Our purpose is to find people who are willing to learn programming and develop their skills', '1995-04-22') ON CONFLICT DO NOTHING;
INSERT INTO institution(address_id, institution_name, description, founded_date)VALUES(5,'Robotics Science', 'Research new technologies to find the best way to create Artificial Intelligence based robots', '1982-08-04') ON CONFLICT DO NOTHING;
-------------------------app_user_institution--------------------------------
INSERT INTO app_user_institution(institution_id, app_user_id) VALUES(1, 1) ON CONFLICT DO NOTHING;
INSERT INTO app_user_institution(institution_id, app_user_id) VALUES(1, 2) ON CONFLICT DO NOTHING;
INSERT INTO app_user_institution(institution_id, app_user_id) VALUES(1, 3) ON CONFLICT DO NOTHING;
INSERT INTO app_user_institution(institution_id, app_user_id) VALUES(1, 4) ON CONFLICT DO NOTHING;

INSERT INTO app_user_institution(institution_id, app_user_id) VALUES(2, 2) ON CONFLICT DO NOTHING;
INSERT INTO app_user_institution(institution_id, app_user_id) VALUES(2, 4) ON CONFLICT DO NOTHING;