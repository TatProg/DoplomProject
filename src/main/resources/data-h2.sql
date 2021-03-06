INSERT INTO `students` (secondName, firstName, lastName, groupNumber,username,password,score) VALUES -- username and password??
  ('Nasyrov','Aydar','Ayratovich',753,'aydar','123',0),
  ('Sokolov','Artem','Eduardovich',750,'sokolovartem','234',0),
  ('Makarov','Vladimir','Imranovich',755,'makarov','345',0),
  ('Pasmurnhov','Alexander','Vladislavovich',752,'sanya','456',10),
  ('Dmitrchuk', 'Arthur', 'Eduardovich', 752,'arturchick','567',10),
  ('Admin','Admin','Admin', 777,'admin','admin',10);

INSERT INTO `moderators` (studentId) VALUES ('6');

INSERT INTO `events` (nameEvent, description, dateEvent) VALUES
  ('День Знаний в Университете','При наличии глаженого и чистого костюма, люди идут в нём','1.09.2018'),
  ('День первокурсника' , 'Грандиозный праздник от первокурсников для разных масс','20.09.2018' ),
  ('День потока Прикладного подоржника' , 'Невероятно уникальные конкурсы по возливанию этиловой амброзии' ,'30.09.2018'),
  ('Зимний квартирник в 9 общежитии', 'Бесплатный и неконтролируемый проход на территорию общежития','1.12.2018'),
  ('Встреча с ректором Университета','Встреча с светлейшей персоной','10.12.2018'),

  ('Встреча рокерских объединений имени Егора Летова и Мика Джаггера','Только рок, только хардкор','15.12.2018'),
  ('Зимний бал в Ратуше','Танцы-обжиманцы и прочее счастье, все как в 19 веке','23.12.2018'),
  ('Встреча Нового Года в ДУ','На улице в последнюю ночь этого года','31.12.2018'),
  ('Создание большого андронного коллайдера','Ну вы поняли, из скотча и палок сделать','1.02.2018'),
  ('Еще одна встреча с ректором','опять нужен человек в костюме','14.02.2018');

INSERT INTO `eventRoles` (eventId, roleName, score) VALUES
  (1, 'ведущий - 7 баллов', 7),
  (2, 'сотрудник технической группы - 5 баллов', 5),
  (2, 'декоратор - 3 балла', 3),
  (2, 'актер - 10 баллов', 10),
  (3, 'тамада - 1 баллов', 1),

  (4, 'тамада', 1),
  (4, 'музыкант', 2),
  (5, 'человек в чистом и опрятном костюме', 100),
  (6, 'музыкант-участник' ,10),
  (6, 'сотрудник технической группы',5),

  (6, 'декоратор',3),
  (6, 'зритель', 1),
  (7, 'танцор', 15),
  (7, 'сотрудник технической группы',5),
  (7, 'декоратор',3),

  (7, 'зритель', 1),
  (8, 'участник', 1),
  (9, 'ученый', 10),
  (9, 'подопытный', 1),
  (10, 'человек в чистом и опрятном костюме', 100);



INSERT INTO `participants` (eventId, roleId, studentId) VALUES
  (1,1,1),
  (2,2,4),
  (2,2,5),
  (2,3,2),
  (2,4,3),
  (3,5,1),
  (3,5,5),
  (4,7,2),
  (4,6,3),
  (5,8,4);


