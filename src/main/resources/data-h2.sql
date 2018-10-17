INSERT INTO `students` (secondName, firstName, lastName, groupNumber) VALUES -- username and password??
  ('Nasyrov','Aydar','Ayratovich',753),
  ('Sokolov','Artem','Eduardovich',750),
  ('Makarov','Vladimir','Imranovich',755),
  ('Pasmurnhov','Alexander','Vladislavovich',752),
  ('Dmitrchuk', 'Arthur', 'Eduardovich', 752);

INSERT INTO `events` (nameEvent, description, dateEvent) VALUES
  ('День Знаний в Университете','При наличии глаженого и чистого костюма, люди идут в нём','1.09.2018'),
  ('День первокурсника' , 'Грандиозный праздник от первокурсников для разных масс','20.09.2018' ),
  ('День потока Прикладного подоржника' , 'Невероятно уникальные конкурсы по возливанию этиловой амброзии' ,'30.09.2018'),
  ('Зимний квартирник в 9 общежитии', 'Бесплатный и неконтролируемый проход на территорию общежития','1.12.2018'),
  ('Встреча с ректором Университета','Встреча с светлейшей персоной','10.12.2018');

INSERT INTO `eventRoles` (eventId, roleName, score) VALUES
  (1, 'ведущий', 7),
  (2, 'сотрудник технической группы', 5),
  (2, 'декоратор', 3),
  (2, 'актер', 10),
  (3, 'тамада', 1),

  (4, 'тамада', 1),
  (4, 'музыкант', 2),
  (5, 'человек в чистом и опрятном костюме', 100);

INSERT INTO `participants` (eventId, roleId, studentId) VALUES
  (1, 1, 1),
  (2,2,4),
  (2,2,5),
  (2,3,2),
  (2,4,3),
  (3,5,1),
  (3,5,5),
  (4,7,2),
  (4,6,3),
  (5,8,4);
