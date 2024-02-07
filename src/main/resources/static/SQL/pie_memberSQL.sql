-- member_user : 회원 데이터 베이스
create table member_user
(
    mem_num integer unique not null,
    id       varchar2(16) primary key,
    password varchar2(20) not null,
    name     varchar2(51) not null,
    nickname varchar2(24) not null,
    gender varchar2(12) not null,
    profile_pic varchar2(500) default 'default.png',
    email varchar2(50),
    phone varchar2(13),
    postCode varchar2(6),
    address_main varchar2(100),
    address_sub varchar2(200),
    agreement char(1) not null,
    friends varchar2(620)
);

create sequence mem_seq nocache nocycle;

-- insert
insert into member_user
values (mem_seq.nextval,
        'admin',
        'admin',
        '관리자',
        '관리자',
        '선택안함',
        'default.png',
        'admin@email.com',
        '010-1111-1111',
        '00000',
        '서울특별시 중구 세종대로 110',
        '000호',
        '1',
        ''
        );
        
-- select
select*from member_user;

-- select_by ID
select*from member_user where id = 'id';

--delete_by ID;
select*from member_user where id = 'id';

--update_by ID;
update member_user set mem_num='', 
                       id='', 
                       password='', 
                       name='', 
                       nickname='', 
                       gender='', 
                       email='', 
                       phone='', 
                       postCode='', 
                       address_main='',
                       address_sub='', 
                       friends='' 
                   where id = 'id';