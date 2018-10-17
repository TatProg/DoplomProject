DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS eventRoles;
DROP TABLE IF EXISTS participants;
DROP TABLE IF EXISTS moderators;

CREATE TABLE IF NOT EXISTS students(
  studentId INTEGER PRIMARY KEY AUTO_INCREMENT,
  secondName VARCHAR(64) NOT NULL,
  firstName VARCHAR(64) NOT NULL,
  lastName VARCHAR(64) NOT NULL,
  groupNumber INTEGER,
  userName VARCHAR(64),
  password VARCHAR(64),
  score INTEGER
);

CREATE TABLE IF NOT EXISTS events(
  eventId INTEGER PRIMARY KEY AUTO_INCREMENT,
  nameEvent TEXT NOT NULL,
  description TEXT NOT NULL,
  dateEvent TEXT,
  success INTEGER --статус проведения мероприятия
);

CREATE TABLE IF NOT EXISTS eventRoles(
  eventRoleId INTEGER PRIMARY KEY AUTO_INCREMENT,
  eventId INTEGER NOT NULL,
  roleName VARCHAR(64) NOT NULL,
  score INTEGER NOT NULL,
  quantity INTEGER --Сколько людей нужно для ивента, убывание до нуля. Если ноль, то не нужны люди.
); -- А нужно ли quantity поле именно сейчас?

CREATE TABLE IF NOT EXISTS participants(
  participantId INTEGER PRIMARY KEY AUTO_INCREMENT,
  eventId INTEGER,
  roleId INTEGER,
  studentId INTEGER,
  state INTEGER --Статус одобрения, а нужен ли он?
);

CREATE TABLE IF NOT EXISTS moderators( --А нужны ли они?
  moderatorId INTEGER PRIMARY KEY AUTO_INCREMENT,
  userId INTEGER
);