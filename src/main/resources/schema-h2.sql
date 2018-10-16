DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS eventRoles;
DROP TABLE IF EXISTS participants;
DROP TABLE IF EXISTS moderators;

CREATE TABLE IF NOT EXISTS students(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  secondName VARCHAR(55) NOT NULL,
  firstName VARCHAR(55) NOT NULL,
  lastName VARCHAR(55) NOT NULL,
  groupNumber INTEGER,
  userName VARCHAR(64),
  password VARCHAR(64),
  score INTEGER
);

CREATE TABLE IF NOT EXISTS events(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nameEvent TEXT NOT NULL,
  description TEXT NOT NULL,
  dateEvent TEXT,
  success INTEGER --статус проведения мероприятия
);

CREATE TABLE IF NOT EXISTS eventRoles(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  eventId INTEGER NOT NULL,
  roleName VARCHAR(55) NOT NULL,
  score INTEGER NOT NULL,
  quantity INTEGER --сколько людей нужно для ивента, убывание до нуля. Если ноль, то не нужны люди
);

CREATE TABLE IF NOT EXISTS participants(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  eventId INTEGER,
  roleId INTEGER,
  userId INTEGER,
  state INTEGER --статус одобрения, а нужен ли он?
);

CREATE TABLE IF NOT EXISTS moderators(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  userId INTEGER
);