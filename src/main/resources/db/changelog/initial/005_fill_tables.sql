--liquibase formatted sql
--changeset Aktan: fill tables

INSERT INTO users (username, password, email, enabled) VALUES
('maria', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'maria@gmail.com', true),
('johndoe', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'john@mail.com', true),
('alexx', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'alex@mail.com', true);

INSERT INTO quizzes (title, description, creator_username) VALUES
('Quiz 1 Title', 'Description of Quiz 1', (select username from users where email like 'maria@gmail.com')),
('Quiz 2 Title', 'Description of Quiz 2', (select username from users where email like 'john@mail.com')),
('Sample Quiz', 'A brief description of the quiz.', 'maria');

INSERT INTO questions (quiz_id, question_text) VALUES
((select id from quizzes where title like 'Quiz 1 Title'), 'First question of Quiz 1'),
((select id from quizzes where title like 'Quiz 1 Title'), 'Second question of Quiz 1'),
((select id from quizzes where title like 'Quiz 2 Title'), 'First question of Quiz 2'),
((SELECT id FROM quizzes WHERE title LIKE 'Sample Quiz'), 'What is the capital of France?'),
((SELECT id FROM quizzes WHERE title LIKE 'Sample Quiz'), 'What is 2 + 2?');

INSERT INTO options (question_id, option_text, is_correct) VALUES
((select id from questions where question_text like 'First question of Quiz 1'), 'Option 1 for Question 1', true),
((select id from questions where question_text like 'First question of Quiz 1'), 'Option 2 for Question 1', false),
((select id from questions where question_text like 'Second question of Quiz 1'), 'Option 1 for Question 2', true),
((select id from questions where question_text like 'First question of Quiz 2'), 'Option 1 for Question 3', false),
((SELECT id FROM questions WHERE question_text LIKE 'What is 2 + 2?'), '3', false),
((SELECT id FROM questions WHERE question_text LIKE 'What is 2 + 2?'), '4', true),
((SELECT id FROM questions WHERE question_text LIKE 'What is 2 + 2?'), '5', false);

INSERT INTO quiz_results (user_username, quiz_id, score, rating) VALUES
((select username from users where email like 'alex@mail.com'), (select id from quizzes where title like 'Quiz 1 Title'), 80, null),
((select username from users where email like 'maria@gmail.com'), (select id from quizzes where title like 'Quiz 2 Title'), 90, null),
('alexx', (SELECT id FROM quizzes WHERE title LIKE 'Sample Quiz'), 80, null);


INSERT INTO AUTHORITIES (ROLE)
VALUES
    ('DEFAULT'),
    ('ADMIN');

INSERT INTO USER_AUTHORITY (USER_USERNAME, AUTHORITY_ID) values
('maria', (select id from AUTHORITIES where role like 'DEFAULT')),
('johndoe', (select id from AUTHORITIES where role like 'DEFAULT')),
('alexx', (select id from AUTHORITIES where role like 'DEFAULT'));


