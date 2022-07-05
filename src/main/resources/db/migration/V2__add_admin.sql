INSERT INTO users(id,archieved,email,name,password,role,bucket_id)
VALUES (1,false,'kklimonov@yandex.ru','admin','admin','ADMIN',null);

ALTER SEQUENCE user_seq RESTART WITH 2;