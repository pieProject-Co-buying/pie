-- 대리공동구매 보드
create table proxyBuyBoard (
    pr_num integer unique not null, -- 게시글 번호
    pr_id varchar2(16) not null, -- 게시글 ID
    pr_nickname  varchar2(30) not null, -- 게시글 닉네임
    pr_category varchar2(30) not null, -- 물품 카테고리
    pr_title varchar2(150) not null, -- 게시글 제목
    pr_content varchar2(3000) not null, -- 게시글 내용
    pr_profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    pr_productImg varchar2(550), -- 게시글 내 이미지
    pr_hit number default 0, -- 조회수 추가
    pr_tag varchar2(50), -- 게시글 태그
    pr_process char(1), -- 모집 진행 여부
    pr_registDay date default sysdate, -- 게시글 등록날짜
    pr_updateDay date default sysdate, -- 게시글 수정 날짜
    pr_deadLine date, -- 모집 마감일자
    pr_personnelMax integer not null, -- 최대 모집인원
    pr_personnelNow integer, -- 현재 모집인원
    pr_priceTotal number, -- 가격 총합
    pr_pricePer number, -- 인당 가격
    pr_ip varchar2(32), -- 게시글 작성 Ip
    pr_like number -- 게시글 좋아요
);
-- 대리공동구매 시퀀스
create sequence pr_num nocache nocycle;

-- 대리공동구매 삽입문
insert into proxyBuyBoard values(
pr_num.nextval,
'testID',
'testNickName',
'testCategory',
'테스트 제목',
'테스트 내용',
'default.png',
'product1.jpg',
0,
'#테스트#게시글',
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

-- 대리공동구매 select
select * from proxyBuyBoard; -- 게시글 전체 선택
select * from proxyBuyBoard order by pr_num desc; -- 게시글 전체 선택 (최신순 나열)
select * from ( select * from proxyBuyBoard order by pr_registDay desc) where ROWNUM <= 3; -- 게시글 지정 개수 출력

select * from proxyBuyBoard where pr_num = 15; -- 게시글 num 으로 선택

-- 대리공동구매 update
update proxybuyboard set pr_nickname='수정닉네임', pr_category='수정카테고리', pr_title='수정제목', pr_content='수정내용', pr_profileImg='update.png', pr_productImg='product2.jpg', pr_tag='#수정#게시글', 
pr_deadLine=TO_DATE('20240307', 'YYYYMMDD'), pr_personnelMax=6 where pr_num=15; -- 전체 업데이트
update proxyBuyBoard set pr_hit = pr_hit+1 where pr_num = 15; -- 조회수 상승

-- 대리공동구매 delete
delete proxyBuyBoard where pr_num = 15; -- 게시글 num 으로 선택

-- 테이블&시퀀스 삭제
drop table proxyBuyBoard;
drop SEQUENCE pr_num;