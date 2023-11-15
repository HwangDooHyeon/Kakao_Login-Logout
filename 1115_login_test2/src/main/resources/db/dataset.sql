-- 모든 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;
truncate table user_tb;
SET REFERENTIAL_INTEGRITY TRUE;
-- 모든 제약 조건 활성화

INSERT INTO user_tb (`id`,`email`,`password`,`userName`, `roles`) VALUES ('1', 'admin@green.com', '{bcrypt}$2a$10$IkVCSNVb.j.63fF19eISZe1mSvMUxs6KRg/ltThRhLexgJzk1CZMO', '홍길동', 'ROLE_ADMIN');
INSERT INTO user_tb (`id`,`email`,`password`,`userName`, `roles`) VALUES ('2', 'user@green.com', '{bcrypt}$2a$10$IkVCSNVb.j.63fF19eISZe1mSvMUxs6KRg/ltThRhLexgJzk1CZMO', '임꺽정', 'ROLE_USER');