-- 동네 공동구매 보드
create table townBuyBoard (
    to_num integer unique not null, -- 게시글 번호
    to_id varchar2(16) not null, -- 게시글 ID
    to_nickname  varchar2(30) not null, -- 게시글 닉네임
    to_category varchar2(30) not null, -- 물품 카테고리
    to_premium char(1), -- 프리미엄 회원 여부
    to_title varchar2(150) not null, -- 게시글 제목
    to_content varchar2(3000) not null, -- 게시글 내용
    to_profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    to_productImg varchar2(550), -- 게시글 내 이미지
    to_hit number, -- 조회수 추가
    to_tag varchar2(50), -- 게시글 태그
    to_address varchar2(150), -- 작성자 주소
    to_process char(1), -- 모집 진행 여부
    to_registDay date default sysdate, -- 게시글 등록날짜
    to_updateDay date default sysdate, -- 게시글 수정 날짜
    to_deadLine date, -- 모집 마감일자
    to_personnelMax integer not null, -- 최대 모집인원
    to_personnelNow integer, -- 현재 모집인원
    to_priceTotal number, -- 가격 총합
    to_pricePer number, -- 인당 가격
    to_ip varchar2(32), -- 게시글 작성 Ip
    to_like number -- 게시글 좋아요
);

-- 동네 공동구매 시퀀스
create sequence to_num nocache nocycle;

-- 동네 공동구매 삽입문
insert into townBuyBoard values(
to_num.nextval,
'testID',
'testNickName',
'testCategory',
'1',
'테스트 제목',
'테스트 내용',
'default.png',
'product1.jpg',
0,
'#테스트#게시글',
'서울시 중구',
'1',
sysdate,
sysdate,
TO_DATE('20240305', 'YYYYMMDD'),
5,
1,
'50000',
'10000',
'111.111.111.111',
0
);

-- 동네 공동구매  select
select * from townBuyBoard; -- 게시글 전체 선택
select * from townBuyBoard order by to_num desc; -- 게시글 전체 선택 (최신순 나열)
select * from ( select * from townBuyBoard order by to_registDay desc) where ROWNUM <= 3; -- 게시글 지정 개수 출력

select * from townBuyBoard where to_num = 15; -- 게시글 num 으로 선택

-- 대리공동구매 update
update townBuyBoard set to_nickname='수정닉네임', to_category='수정카테고리', to_title='수정제목', to_content='수정내용', to_profileImg='update.png', to_productImg='product2.jpg', to_tag='#수정#게시글', 
to_deadLine=TO_DATE('20240307', 'YYYYMMDD'), to_personnelMax=6 where to_num=15; -- 전체 업데이트
update townBuyBoard set to_hit = to_hit+1 where to_num = 15; -- 조회수 상승

-- 대리공동구매 delete
delete townBuyBoard where to_num = 15; -- 게시글 num 으로 선택

-- 테이블 삭제
drop table townBuyBoard;