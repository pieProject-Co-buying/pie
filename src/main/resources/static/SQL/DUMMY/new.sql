-- townBoard 생성         
create table townBuyBoard (
    num integer unique not null, -- 게시글 번호
    id varchar2(16) not null, -- 게시글 ID
    nickname  varchar2(30) not null, -- 게시글 닉네임
    category varchar2(30) not null, -- 물품 카테고리
    premium char(1), -- 프리미엄 회원 여부
    title varchar2(150) not null, -- 게시글 제목
    content varchar2(3000) not null, -- 게시글 내용
    profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    productImg varchar2(1000), -- 게시글 내 이미지
    hit number, -- 조회수 추가
    tag varchar2(50), -- 게시글 태그
    address varchar2(150), -- 작성자 주소
    process char(1), -- 모집 진행 여부
    registDay date default sysdate, -- 게시글 등록날짜
    updateDay date default sysdate, -- 게시글 수정 날짜
    deadLine date, -- 모집 마감일자
    personnelMax integer not null, -- 최대 모집인원
    personnelNow integer, -- 현재 모집인원
    priceTotal number, -- 가격 총합
    pricePer number, -- 인당 가격
    ip varchar2(32), -- 게시글 작성 Ip
    likeNum number, -- 게시글 좋아요
    brand varchar2(300),
    productName varchar2(300)
);

-- townBoard 시퀀스 생성
create sequence to_num nocache nocycle;

-- 대리공동구매 보드
create table proxyBuyBoard (
    num integer unique not null, -- 게시글 번호
    id varchar2(16) not null, -- 게시글 ID
    nickname  varchar2(30) not null, -- 게시글 닉네임
    category varchar2(30) not null, -- 물품 카테고리
    title varchar2(150) not null, -- 게시글 제목
    content varchar2(3000) not null, -- 게시글 내용
    profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    productImg varchar2(550), -- 게시글 내 이미지
    hit number default 0, -- 조회수 추가
    tag varchar2(50), -- 게시글 태그
    process char(1), -- 모집 진행 여부
    registDay date default sysdate, -- 게시글 등록날짜
    updateDay date default sysdate, -- 게시글 수정 날짜
    deadLine date, -- 모집 마감일자
    personnelMax integer not null, -- 최대 모집인원
    personnelNow integer, -- 현재 모집인원
    priceTotal number, -- 가격 총합
    pricePer number, -- 인당 가격
    ip varchar2(32), -- 게시글 작성 Ip
    likeNum number, -- 게시글 좋아요
     brand varchar2(300),
    productName varchar2(300)
);

-- 대리공동구매 시퀀스
create sequence pr_num nocache nocycle;

-- 서비스 공유 보드
create table shareServiceBoard (
    num integer not null, -- 게시글 번호
    id varchar2(16) not null, -- 게시글 ID
    nickname  varchar2(30) not null, -- 게시글 닉네임
    category varchar2(30) not null, -- 물품 카테고리
    premium char(1), -- 프리미엄 회원 여부
    title varchar2(150) not null, -- 게시글 제목
    content varchar2(3000) not null, -- 게시글 내용
    profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    productImg varchar2(550), -- 게시글 내 이미지
    hit number, -- 조회수 추가
    tag varchar2(50), -- 게시글 태그
    process char(1), -- 모집 진행 여부
    registDay date default sysdate, -- 게시글 등록날짜
    updateDay date default sysdate, -- 게시글 수정 날짜
    deadLine date, -- 모집 마감일자
    personnelMax integer not null, -- 최대 모집인원
    personnelNow integer, -- 현재 모집인원
    priceTotal number, -- 가격 총합
    pricePer number, -- 인당 가격
    ip varchar2(32), -- 게시글 작성 Ip
    likeNum number, -- 게시글 좋아요
   brand varchar2(300),
    productName varchar2(300)
);

select* from makefeed;

select * from (select * from proxyBuyBoard where category= 'baby'
		and process=1
		order by updateDay desc) where ROWNUM  <= 10;
        
        select* from proxyBuyBoard;

-- 서비스 공유 시퀀스
create sequence sh_num nocache nocycle;

-- 대리공구 신청 글
create table proxyApplyBoard(
  num integer unique not null, -- 게시글 번호
    id varchar2(16) not null, -- 게시글 ID
    nickname  varchar2(30) not null, -- 게시글 닉네임
    category varchar2(30) not null, -- 물품 카테고리
    title varchar2(150) not null, -- 게시글 제목
    content varchar2(3000) not null, -- 게시글 내용
    profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    productImg varchar2(550), -- 게시글 내 이미지
    process number(1) not null, -- 모집 진행 여부
    registDay date default sysdate, -- 게시글 등록날짜
    updateDay date default sysdate, -- 게시글 수정 날짜
    chkDay date default sysdate,
    ip varchar2(32), -- 게시글 작성 Ip
    URL varchar2(300)
);

-- 대리공구 시퀀스
create sequence applyNum NOCACHE NOCYCLE;

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
	
    bus_status VARCHAR2(100) DEFAULT 'inProgress',
    bus_writeDay DATE DEFAULT SYSDATE
);


drop table businessApplyForm ;



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