create table member_auth(
id varchar2(20),
auth varchar2(30)
);

insert into member_auth values ('admin', 'ROLE_ADMIN');

select * from member_auth

commit;