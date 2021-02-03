insert into questions (title, content, creation_date, status, user_id, revature_question, location_id)
VALUES ('question 6', 'question 6 content', '2012-12-12', false, 13, false, 1),
('question 7', 'question 7 content', '2012-12-12', false, 13, false, 2),
('question 8', 'question 8 content', '2012-12-12', false, 13, true, 2),
('question 9', 'question 9 content', '2012-12-12', false, 13, true, 1),
('question 10', 'question 10 content', '2012-12-12', false, 13, true, 3);
insert into questions (title, content, creation_date, status, user_id, revature_question)
VALUES ('question 11', 'question 6 content', '2012-12-12', false, 13, false),
('question 12', 'question 7 content', '2012-12-12', false, 13, false),
('question 13', 'question 8 content', '2012-12-12', false, 13, true),
('question 14', 'question 9 content', '2012-12-12', false, 13, true),
('question 15', 'question 10 content', '2012-12-12', false, 13, true);
insert into questions (title, content, creation_date, status, user_id)
VALUES ('question 16', 'question 16 content', '2012-12-12', false, 13),
('question 18', 'question 18 content', '2012-12-12', false, 13),
('question 19', 'question 19 content', '2012-12-12', false, 13),
('question 20', 'question 20 content', '2012-12-12', false, 13),
('question 21', 'question 21 content', '2012-12-12', false, 13);
insert into questions (title, content, creation_date, status, user_id)
VALUES ('question 22', 'question 22 content', '2012-12-12', false, 13),
('question 23', 'question 23 content', '2012-12-12', false, 13),
('question 24', 'question 24 content', '2012-12-12', false, 13),
('question 25', 'question 25 content', '2012-12-12', false, 13),
('question 26', 'question 26 content', '2012-12-12', false, 13);
insert into questions (title, content, creation_date, status, user_id)
VALUES ('question 27', 'question 27 content', '2012-12-12', false, 13),
('question 28', 'question 28 content', '2012-12-12', false, 13),
('question 29', 'question 29 content', '2012-12-12', false, 13),
('question 30', 'question 30 content', '2012-12-12', false, 13),
('question 31', 'question 31 content', '2012-12-12', false, 13);

insert into answers (user_id, question_id, content, creation_date, edit_date)
VALUES(14, 1, 'answer content 1', '2012-12-13', NULL), 
(14, 1, 'answer content 2', '2012-12-13', NULL),
(14, 1, 'answer content 3', '2012-12-13', NULL), 
(13, 2, 'answer content 4', '2012-12-13', NULL),  
(13, 2, 'answer content 5', '2012-12-13', NULL);

insert into faq (faq_id, question_id, answer_id, location) values(1,1,1,'Toronto');
insert into faq (faq_id, question_id, answer_id, location) values(2,2,2,'Ottawa');

SELECT questions.* from questions;
SELECT answers.* from answers;
SELECT users.* from users;
SELECT faq.* from faq;
SELECT locations.* from locations;