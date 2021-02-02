DROP TABLE if exists questions CASCADE;
DROP TABLE IF EXISTS answers CASCADE;
DROP TABLE IF EXISTS faq CASCADE;
DROP TABLE IF EXISTS users;
DROP TABLE if exists locations CASCADE;

CREATE TABLE users (
  user_id int PRIMARY KEY,
  account_id int NOT NULL,
  points int NOT NULL,
  admin_role boolean NOT NULL,
  profile_picture BYTEA,
  email varchar(250) not null,
  first_name varchar(250) NOT NULL,
  last_name varchar(250) NOT NULL,
  "PASSWORD" varchar(250) NOT NULL
);

create table locations (
	id INTEGER generated always as identity primary key,
	location_name VARCHAR(50) NOT NULL
);

create table questions (
	id INTEGER generated always as identity primary key,
	accepted_id INTEGER,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(1000) NOT NULL,
	creation_date TIMESTAMP NOT NULL,
	edit_date TIMESTAMP,
	revature_question BOOLEAN DEFAULT FALSE, 
	status BOOLEAN NOT NULL,
	user_id INTEGER references users(user_id),
	location_id INTEGER references locations(id)
);

create table answers (
	id INTEGER generated always as identity primary key,
	user_id INTEGER references users(user_id),
	question_id INTEGER references questions(id),
	content VARCHAR(1000),
	creation_date TIMESTAMP NOT NULL,
	edit_date TIMESTAMP
);


DROP TABLE IF EXISTS faq CASCADE;
create table faq (
	id INTEGER generated always as identity primary key,
	question_id INTEGER references questions(id),
	answer_id INTEGER references answers(id)
);

INSERT INTO users (user_id,account_id, points, admin_role, profile_picture, email, first_name, last_name, "PASSWORD")
VALUES  (14, 16, 100, true, NULL, 'test@revature.com', 'User', 'User', 'password'), (13, 15, 100, false, NULL, 'user@rss.com', 'User', 'User', '$2a$10$97GCVEL7iXNgR8MuSltN9.pHHXnNNjU0c.uoVpuYWHMgRKOljdS26'), (12, 26, 20, true, NULL, 'admin@rss.com', 'Admin', 'Admin', '$2a$10$97GCVEL7iXNgR8MuSltN9.pHHXnNNjU0c.uoVpuYWHMgRKOljdS26');

insert into locations (location_name)
values ('All Locations'),
('Reston'),
('Toronto'),
('Tampa'),
('New York'),
('Dallas'),
('Orlando'),
('Morgantown');
