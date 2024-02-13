
-- townBoard 생성         
                   
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
                   


