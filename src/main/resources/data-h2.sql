INSERT INTO `students` (secondName, firstName, lastName, groupNumber) VALUES -- username and password??
  ('Nasyrov','Aydar','Ayratovich',753),
  ('Sokolov','Artem','Eduardovich',750),
  ('Makarov','Vladimir','Imranovich',755),
  ('Pasmurnhov','Alexander','Vladislavovich',752),
  ('Dmitrchuk', 'Arthur', 'Eduardovich', 752);

INSERT INTO `events` (nameEvent, description, dateEvent) VALUES
  ('День первокурсника' , 'Грандиозный праздник от первокурсников для разных масс','20.09.2018' ),
  ('День потока Прикладного подоржника' , 'Невероятно уникальные конкурсы по возливанию этиловой амброзии' ,'31.09.2018'),
  ('Зимний квартирник в 9 общежитии', 'Бесплатный и неконтролируемый проход на территорию общежития','1.12.2018'),
  ('Встреча с ректором Университета','Встреча с светлейшей персоной','10.10.2018');

INSERT INTO `eventRoles` (eventId, roleName, score) VALUES
  (1, 'сотрудник технической группы', 5),
  (1, 'декоратор', 3),
  (1, 'актер', 10),
  (2, 'тамада', 1),
  (3, 'музыкант', 2),
  (4, 'человек в чистом и опрятном костюме', 100);


