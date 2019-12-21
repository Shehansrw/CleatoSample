-- select database();
-- use `auth-api`;

-- SELECT * FROM `auth-api`.`user` as u;

-- INSERT INTO `auth-api`.`user`(id,firstName,lastName,password,salary,age) VALUES (1, "Alex","Knr", "alex123","$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2", 3456, 33);

INSERT INTO user (id, firstname, lastname, username, password, salary, age ,active) VALUES (1, 'admin','admin', 'admin','$2a$10$mVFm7qpJG66YSQN53hEd9.f7.goTe76iVW9LYXVJ.EFzUdn7Ufvey', 3456, 33,'a');
INSERT INTO user (id, firstname, lastname, username, password, salary, age,active)  VALUES (2, 'Shehan', 'Wijesinghe', 'shehan', '$2a$10$mVFm7qpJG66YSQN53hEd9.f7.goTe76iVW9LYXVJ.EFzUdn7Ufvey', 25000, 28,'a');
INSERT INTO user (id, firstname, lastname, username, password, salary, age,active)  VALUES (3, 'Tom', 'Asr', 'tom234', '$2a$10$mVFm7qpJG66YSQN53hEd9.f7.goTe76iVW9LYXVJ.EFzUdn7Ufvey', 7823, 23,'a');
INSERT INTO user (id, firstname, lastname, username, password, salary, age,active)  VALUES (4, 'Adam', 'Psr', 'adam', '$2a$10$mVFm7qpJG66YSQN53hEd9.f7.goTe76iVW9LYXVJ.EFzUdn7Ufvey', 4234, 45,'a');
