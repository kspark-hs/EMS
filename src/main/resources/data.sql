-- 관리자 계정
insert into members
(email, password, name, phone, authority, is_active)
values
(
 'ksparkhs@naver.com',
 '$2a$10$6ReCTOUT0zilTaQokJqTfORYp3/Rb6tnLmnrY9GW4rf8i9k96Q9oG',
 '박강석',
 '010-6568-8569',
 'ROLE_ADMIN',
 true
);

-- 일반 사용자 계정
insert into members
(email, password, name, phone, authority, is_active)
values
(
 'sbtrchs@korea.kr',
 '$2a$10$6ReCTOUT0zilTaQokJqTfORYp3/Rb6tnLmnrY9GW4rf8i9k96Q9oG',
 '최행선',
 '010-6555-8561',
 'ROLE_USER',
 true
);
