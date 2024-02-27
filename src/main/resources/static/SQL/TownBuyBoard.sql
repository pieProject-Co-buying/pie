
-- townBoard 생성         
                   
create table townBuyBoard (
    to_num integer unique not null, -- 게시글 번호
    to_id varchar2(16),
    to_nickname  varchar2(30),
    to_category varchar2(30) not null, -- 물품 카테고리
    to_premium char(1), -- 프리미엄 회원 여부
    to_title varchar2(150) not null,
    to_content varchar2(3000) not null,
    to_price varchar2(100) not null,
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




-- 시퀀스
-- 시퀀스 생성
create sequence to_num nocache nocycle;
-- 시퀀스 삭제
drop sequence to_num;


-- 테이블 삭제
drop table townBuyBoard purge;






-- ########### 데이터 수정 ###########

update townBuyBoard 
set to_category = 'food', 
    to_title = '감자가 아니라 카메라였네요 죄송죄송',
    to_content = '감자가 아니라 카메라였네요 죄송죄송',
    to_price = '500000000000', 
    to_personnelMax = '100',  
    to_deadLine = TO_DATE('2024-02-15', 'YYYY-MM-DD') 
where to_num = 9;


commit;



--커밋
commit;



-- ############## 데이터 입력 ##############
insert into townBuyBoard values(
mem_seq.nextval,
'soloKim',
'soloKim',
'육아',
'1',
'감자 같이 사실분?',
'너무 많아서 같이 사고 싶어용~',
'4000',
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


insert into townBuyBoard (to_num, to_category, to_title, to_content, to_price, to_personnelMax, to_deadLine) values(mem_seq.nextval, 'baby', '감자 팔아용', '감자 많아요 감자감자', '4000', '8', TO_DATE('20240210', 'YYYYMMDD'));

insert into townBuyBoard (to_num, to_category, to_title, to_content, to_price, to_personnelMax, to_deadLine) values(to_num.nextval, 'baby', '감자 팔아용', '감자 많아요 감자감자', '4000', '8', TO_DATE('2024-02-10', 'YYYY-MM-DD'));



-- ############## 시퀀스로 데이터 삭제 ##############

delete townBuyBoard where to_num = 63;





-- 전체 테이블 보기
select * from townBuyBoard;

-- 내림차순으로 보기(시퀀스 기준)
select * from townBuyBoard order by to_num desc;

-- 진행중(1) 인 공구 카테고리 보기
select to_title, to_content, to_productImg, to_profileImg from townBuyBoard where to_process = 1 and to_category= '육아';

select to_title, to_content, to_productImg, to_profileImg from townBuyBoard where to_process = 1 and to_category= '육아';

-- 게시글 번호 기준으로 진행중, 카테고리 
select to_title, to_content, to_productImg, to_profileImg from townBuyBoard where to_title = '아기옷';


select to_title, to_content, to_productImg, to_profileImg
from townBuyBoard
where to_title like '%'||#{param1}||'%' OR to_content LIKE '%'||#{param1}||'%';


-- 검색어 입력 
SELECT to_title, to_content, to_productImg, to_profileImg FROM townBuyBoard WHERE to_title LIKE '%감자%' OR to_content LIKE '%감자%';


-- 게시글 번호 기준으로 진행중, 카테고리 
select to_title, to_content, to_productImg, to_profileImg from townBuyBoard where to_num = 50 and to_process = 1 and to_category='육아';

select * from townBuyBoard where to_num = '119';


select * from townBuyBoard where to_category = '육아';









commit;



commit;


