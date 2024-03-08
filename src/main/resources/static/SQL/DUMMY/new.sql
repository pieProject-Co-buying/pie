-- townBoard 생성         
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

-- townBoard 시퀀스 생성
create sequence to_num nocache nocycle;

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

-- 대리공구 신청 글
create table proxyApplyBoard(
  pr_num integer unique not null, -- 게시글 번호
    pr_id varchar2(16) not null, -- 게시글 ID
    pr_nickname  varchar2(30) not null, -- 게시글 닉네임
    pr_category varchar2(30) not null, -- 물품 카테고리
    pr_title varchar2(150) not null, -- 게시글 제목
    pr_content varchar2(3000) not null, -- 게시글 내용
    pr_profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    pr_productImg varchar2(550), -- 게시글 내 이미지
    pr_process number(1) not null, -- 모집 진행 여부
    pr_registDay date default sysdate, -- 게시글 등록날짜
    pr_updateDay date default sysdate, -- 게시글 수정 날짜
    pr_chkDay date default sysdate,
    pr_ip varchar2(32), -- 게시글 작성 Ip
    pr_URL varchar2(300)
);

-- 대리공구 시퀀스
create sequence pr_applyNum NOCACHE NOCYCLE;

-- 좋아요 테이블
create table likeTable(
likingId varchar2(16),
boardNum number,
boardTable varchar2(50)
);

-- 참여확인 테이블
create table checkParticipate(
boardNum number,
tableName varchar2(20),
mem varchar2(20),
partiDate date,
status char(1),
paid char(1)
);

--feed 정보 테이블
create table makeFeed(
id varchar(20),
feed1 varchar(50),
feed2 varchar(50),
feed3 varchar(50)
);

-- 결제정보 테이블
create table payment(
    pay_num integer not null,
    buyer_id varchar2(100),
    buyer_name varchar2(100),
    buyer_nickname varchar2(100),
    buyer_tel varchar2(100),
    buyer_addr varchar2(100),
    buyer_email varchar2(100),
    buyer_postcode varchar2(100),
    pay_uid varchar2(100),
    pay_method varchar2(100),
    pay_merchant_uid varchar2(100) unique not null,
    pay_name varchar2(100),
    pay_amount number,
    pay_date date default sysdate,
    pay_category varchar2(100),
    pay_refund varchar2(10) default '0'
);

--구독 결제내역
create table subScribe(
    sub_num integer unique not null,
    buyer_id varchar2(100),
    buyer_name varchar2(100),
    buyer_nickname varchar2(100),
    buyer_tel varchar2(100),
    buyer_email varchar2(100),
    schedules number,
    sub_uid varchar2(100),
    sub_customer_uid varchar2(100) unique not null,
    sub_method varchar2(100),
    sub_merchant_uid varchar2(100) unique not null,
    sub_name varchar2(100),
    sub_amount number,
    sub_date date default sysdate,
    sub_premium varchar2(100)
);
-- 구독결제내역 시퀀스
create sequence sub_num nocache nocycle;


--검색어인기차트
create table searchKey(
keyWord varchar2(150),
tableName varchar2(30),
hit number
);

-- 기업신청 테이블

CREATE TABLE businessApplyForm (
    bus_apply_num NUMBER UNIQUE NOT NULL,
    
    bus_title VARCHAR2(100) NOT NULL,
    bus_content VARCHAR2(2000) NOT NULL,
    bus_img VARCHAR2(200),
    
    bus_name VARCHAR2(100) NOT NULL,
    bus_num VARCHAR2(100) NOT NULL,
    bus_postCode VARCHAR2(300),
    bus_address_main VARCHAR2(300),    
    bus_address_sub VARCHAR2(300),   
    bus_productName VARCHAR2(100) NOT NULL,   
    bus_Maxqnt NUMBER NOT NULL,
    bus_unitPrice NUMBER NOT NULL,
 
    bus_chargePerson VARCHAR2(100) NOT NULL,
    bus_phone VARCHAR2(100) NOT NULL,
    bus_email VARCHAR2(100) NOT NULL,
    
    bus_password VARCHAR2(30) NOT NULL,
    
    bus_hit NUMBER,
    bus_writeDay DATE DEFAULT SYSDATE
);

-- 시퀀스 생성/삭제
create sequence bus_apply_num nocache nocycle;

create table friendTable(
followingId varchar2(30),
followedId varchar2(30)
);

select DISTINCT keyWord from ( select * from searchKey order by hit
		desc)
		where ROWNUM  <=
		25;