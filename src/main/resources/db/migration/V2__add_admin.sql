INSERT INTO users(id,archieved,email,name,password,role)
VALUES (1,false,'kklimonov@yandex.ru','admin','$2a$10$MweAyiIsgaPmSPMAzLWD/ei0O7PO8/xoibKksXkx6Hml3xd/dGwIK','ADMIN');

ALTER SEQUENCE user_seq RESTART WITH 2;