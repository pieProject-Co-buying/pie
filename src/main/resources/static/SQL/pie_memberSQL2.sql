-- member_user : 회원 데이터 베이스
create table member_user
(
    mem_num integer unique not null,
    id       varchar2(16) primary key,
    password varchar2(100) not null,
    salt varchar2(40) not null,
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
    friends varchar2(620),
    premium varchar2(4) default 'none',
    preDate date default sysdate,
    preEndDate date default sysdate+30
);



drop table member_user;
drop sequence mem_seq;

create sequence mem_seq nocache nocycle;
commit;

update member_user set preEndDate = sysdate;
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
        '',
        'pro'
        );
        
select preEndDate from member_user;

update member_user set preenddate=to_date('2024-02-16 09:19:27', 'YYYY-MM-DD HH24:MI:SS');
commit;  
ROLLBACK;
        
-- select
select*from member_user;

select salt from member_user;

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
                   
update member_user set premium = 'none';
                   

-- townBoard 생성         
                   
-- 테이블 삭제
drop table townBuyBoard purge;

-- 시퀀스
-- 시퀀스 생성
create sequence to_num nocache nocycle;
-- 시퀀스 삭제
drop sequence to_num;

--커밋
commit;

-- 데이터 입력
insert into townBuyBoard values(
mem_seq.nextval,
'soloKim',
'soloKim',
'육아',
'1',
'감자 같이 사실분?',
'너무 많아서 같이 사고 싶어용~',
'person1.jpg',
'product1.jpg',
0,
'감자',
'서울시 중구',
'1',
sysdate,
sysdate,
TO_DATE('20240210', 'YYYYMMDD'),
5,
2,
'123'
);


-- 전체 테이블 보기
select * from townBuyBoard;


select to_title, to_content from townBuyBoard where to_num = ;


update member_user set premium = 'none' where id = 'admin';

















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
                   

create table testDB(
title varchar(100),
contents varchar(500)
);

commit;

select * from testDB;

create table shareServiceBoard(
    sh_numID number primary key,
    sh_id varchar2(100) not null,--
    sh_nickname varchar2(100) not null,
    sh_title varchar2(100) not null,
    sh_content varchar2(100) not null,
    sh_category varchar2(100) not null,
    sh_price integer not null,
    sh_process varchar2(10) not null,
    sh_registDay date,
    sh_updateDay date default sysdate,
    sh_DeadLine date not null,
    sh_personnelMax integer,
    sh_personnelNow integer,
    sh_ip varchar2(32) not null--
);

create sequence sh_numID nocycle nocache;

insert into shareServiceBoard values (sh_numID.nextval,'admin1','닉네임1','넷플릭스 공구 모집','넷플릭스 공구 모집합니다.','OTT',90000,'진행중',TO_DATE('2024-02-08', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,10,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin2','닉네임2','왓챠 공구 모집','왓챠 공구 모집합니다.','OTT',80000,'진행중',TO_DATE('2024-02-03', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,1,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin3','닉네임3','쿠팡플레이 공구 모집','쿠팡플레이 공구 모집합니다.','OTT',70000,'진행중',TO_DATE('2024-02-05', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,3,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin4','닉네임4','디즈니 공구 모집','디즈니 공구 모집합니다.','OTT',130000,'진행중',TO_DATE('2024-02-06', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,8,'486.486.486.6');

create table townBuyBoard (
    to_num integer unique not null, -- 게시글 번호
    to_id varchar2(16) not null,
    to_nickname  varchar2(30) not null,
    to_category varchar2(30) not null, -- 물품 카테고리
    to_premium char(1), -- 프리미엄 회원 여부
    to_title varchar2(150) not null,
    to_content varchar2(3000) not null,
    to_profileImg varchar2(50), -- 이미지 추가
    to_productImg varchar2(50), -- 이미지 추가
    to_hit number, -- 조회수 추가
    to_tag varchar2(50),
    to_address varchar2(150),
    to_process char(1),
    to_registDay date default sysdate,
    to_updateDay date default sysdate,
    to_deadLine date,
    to_personnelMax integer not null,
    to_personnelNow integer,
    to_ip varchar2(32)
);


create sequence to_num nocache nocycle;

insert into townBuyBoard values(
mem_seq.nextval,
'soloKim',
'soloKim',
'식품',
'1',
'감자 같이 사실분?',
'너무 많아서 같이 사고 싶어용~',
'person1.jpg',
'product1.jpg',
0,
'감자',
'서울시 중구',
'1',
sysdate,
sysdate,
TO_DATE('20240210', 'YYYYMMDD'),
5,
2,
'123'
);

drop table proxyBuyBoard;

create table proxyBuyBoard (
    pr_num integer unique not null, -- 게시글 번호
    pr_id varchar2(16) not null,
    pr_nickname  varchar2(30) not null,
    pr_category varchar2(30) not null, -- 물품 카테고리
    pr_premium char(1), -- 프리미엄 회원 여부
    pr_title varchar2(150) not null,
    pr_content varchar2(3000) not null,
    pr_profileImg varchar2(50), -- 이미지 추가
    pr_productImg varchar2(550), -- 이미지 추가
    pr_hit number, -- 조회수 추가
    pr_tag varchar2(50),
    pr_address varchar2(150),
    pr_process char(1),
    pr_registDay date default sysdate,
    pr_updateDay date default sysdate,
    pr_deadLine date,
    pr_personnelMax integer not null,
    pr_personnelNow integer,
    pr_priceTotal number,
    pr_pricePer number,
    pr_ip varchar2(32),
    pr_like number
);
update proxyBuyBoard set pr_hit = pr_hit+1;
commit;

select * from proxyBuyBoard order by pr_registDay desc;

insert into proxyBuyBoard values(
pr_num.nextval,
'soloKim',
'soloKim',
'육아',
'1',
'감자 같이 사실분?',
'너무 많아서 같이 사고 싶어용~',
'person1.jpg',
'product1.jpg',
0,
'감자',
'서울시 중구',
'1',
sysdate,
sysdate,
TO_DATE('20240210', 'YYYYMMDD'),
5,
2,
'123'
);

select * from proxyBuyBoard;

create sequence pr_num nocache nocycle;

update proxyBuyBoard set pr_like = (select count(*) from likeTable where boardNum = 23) where pr_num = 23;

commit;

create table likeTable(
likingId varchar2(16),
boardNum number,
boardTable varchar2(50)
);

select * from likeTable;

select count(*)from likeTable where likingId = '1' boardNum=  '1' boardTable = '1';

drop table likeTable;