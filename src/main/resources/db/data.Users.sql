
truncate table users cascade;
truncate table jobs cascade;
insert into users(id,	email,	firstname,	lastname,	password , role)values
 (5	,'fatoye@gmail.com',	'ay',	'fatoye'	,'$2a$10$pV9pUdml/W9j/AE6tqTTcuVEps9BzLS4yo7gBWZEknq8Tc5KkXMfy','{"USER"}');

 insert into jobs(id, name ,description, amount,category,location)values
  (1,'job','job','100','menial','lagos');



