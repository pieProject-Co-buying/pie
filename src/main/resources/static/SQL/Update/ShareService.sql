-- 서비스 공유 보드
create table shareServiceBoard (
    sh_num integer not null, -- 게시글 번호
    sh_id varchar2(16) not null, -- 게시글 ID
    sh_nickname  varchar2(30) not null, -- 게시글 닉네임
    sh_category varchar2(30) not null, -- 물품 카테고리
    sh_premium char(1), -- 프리미엄 회원 여부
    sh_title varchar2(150) not null, -- 게시글 제목
    sh_content varchar2(3000) not null, -- 게시글 내용
    sh_profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    sh_productImg varchar2(550), -- 게시글 내 이미지
    sh_hit number, -- 조회수 추가
    sh_tag varchar2(50), -- 게시글 태그
    sh_process char(1), -- 모집 진행 여부
    sh_registDay date default sysdate, -- 게시글 등록날짜
    sh_updateDay date default sysdate, -- 게시글 수정 날짜
    sh_deadLine date, -- 모집 마감일자
    sh_personnelMax integer not null, -- 최대 모집인원
    sh_personnelNow integer, -- 현재 모집인원
    sh_priceTotal number, -- 가격 총합
    sh_pricePer number, -- 인당 가격
    sh_ip varchar2(32), -- 게시글 작성 Ip
    sh_like number, -- 게시글 좋아요
    sh_product varchar2(30) -- 상품넘버
);

-- 서비스 공유 시퀀스
create sequence sh_num nocache nocycle;
drop sequence sh_num;

-- 서비스 공유 삽입문
insert into shareServiceBoard values(
sh_num.nextval,
'admin',
'testNickName',
'testCategory',
'1',
'테스트 제목1',
'테스트 내용',
'boog.png',
'product1.jpg/',
0,
'#테스트#게시글',
'1',
sysdate,
sysdate,
TO_DATE('20240305', 'YYYYMMDD'),
3000,
1,
'3000',
'1',
'111.111.111.111',
0,
null
);

-- 서비스 공유  select
select * from shareServiceBoard; -- 게시글 전체 선택
select * from shareServiceBoard order by sh_num desc; -- 게시글 전체 선택 (최신순 나열)
select * from ( select * from shareServiceBoard order by sh_registDay desc) where ROWNUM <= 3; -- 게시글 지정 개수 출력

select * from shareServiceBoard where sh_num = 15; -- 게시글 num 으로 선택
update shareServiceBoard set sh_process = '0' where sh_num = 1;
drop table shareServiceBoard;
commit;