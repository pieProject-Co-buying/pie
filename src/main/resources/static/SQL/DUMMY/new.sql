-- townBoard ����         
create table townBuyBoard (
    num integer unique not null, -- �Խñ� ��ȣ
    id varchar2(16) not null, -- �Խñ� ID
    nickname  varchar2(30) not null, -- �Խñ� �г���
    category varchar2(30) not null, -- ��ǰ ī�װ�
    premium char(1), -- �����̾� ȸ�� ����
    title varchar2(150) not null, -- �Խñ� ����
    content varchar2(3000) not null, -- �Խñ� ����
    profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    productImg varchar2(1000), -- �Խñ� �� �̹���
    hit number, -- ��ȸ�� �߰�
    tag varchar2(50), -- �Խñ� �±�
    address varchar2(150), -- �ۼ��� �ּ�
    process char(1), -- ���� ���� ����
    registDay date default sysdate, -- �Խñ� ��ϳ�¥
    updateDay date default sysdate, -- �Խñ� ���� ��¥
    deadLine date, -- ���� ��������
    personnelMax integer not null, -- �ִ� �����ο�
    personnelNow integer, -- ���� �����ο�
    priceTotal number, -- ���� ����
    pricePer number, -- �δ� ����
    ip varchar2(32), -- �Խñ� �ۼ� Ip
    likeNum number, -- �Խñ� ���ƿ�
    brand varchar2(300),
    productName varchar2(300)
);

-- townBoard ������ ����
create sequence to_num nocache nocycle;

-- �븮�������� ����
create table proxyBuyBoard (
    num integer unique not null, -- �Խñ� ��ȣ
    id varchar2(16) not null, -- �Խñ� ID
    nickname  varchar2(30) not null, -- �Խñ� �г���
    category varchar2(30) not null, -- ��ǰ ī�װ�
    title varchar2(150) not null, -- �Խñ� ����
    content varchar2(3000) not null, -- �Խñ� ����
    profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    productImg varchar2(550), -- �Խñ� �� �̹���
    hit number default 0, -- ��ȸ�� �߰�
    tag varchar2(50), -- �Խñ� �±�
    process char(1), -- ���� ���� ����
    registDay date default sysdate, -- �Խñ� ��ϳ�¥
    updateDay date default sysdate, -- �Խñ� ���� ��¥
    deadLine date, -- ���� ��������
    personnelMax integer not null, -- �ִ� �����ο�
    personnelNow integer, -- ���� �����ο�
    priceTotal number, -- ���� ����
    pricePer number, -- �δ� ����
    ip varchar2(32), -- �Խñ� �ۼ� Ip
    likeNum number, -- �Խñ� ���ƿ�
     brand varchar2(300),
    productName varchar2(300)
);

-- �븮�������� ������
create sequence pr_num nocache nocycle;

-- ���� ���� ����
create table shareServiceBoard (
    num integer not null, -- �Խñ� ��ȣ
    id varchar2(16) not null, -- �Խñ� ID
    nickname  varchar2(30) not null, -- �Խñ� �г���
    category varchar2(30) not null, -- ��ǰ ī�װ�
    premium char(1), -- �����̾� ȸ�� ����
    title varchar2(150) not null, -- �Խñ� ����
    content varchar2(3000) not null, -- �Խñ� ����
    profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    productImg varchar2(550), -- �Խñ� �� �̹���
    hit number, -- ��ȸ�� �߰�
    tag varchar2(50), -- �Խñ� �±�
    process char(1), -- ���� ���� ����
    registDay date default sysdate, -- �Խñ� ��ϳ�¥
    updateDay date default sysdate, -- �Խñ� ���� ��¥
    deadLine date, -- ���� ��������
    personnelMax integer not null, -- �ִ� �����ο�
    personnelNow integer, -- ���� �����ο�
    priceTotal number, -- ���� ����
    pricePer number, -- �δ� ����
    ip varchar2(32), -- �Խñ� �ۼ� Ip
    likeNum number, -- �Խñ� ���ƿ�
   brand varchar2(300),
    productName varchar2(300)
);

select* from makefeed;

select * from (select * from proxyBuyBoard where category= 'baby'
		and process=1
		order by updateDay desc) where ROWNUM  <= 10;
        
        select* from proxyBuyBoard;

-- ���� ���� ������
create sequence sh_num nocache nocycle;

-- �븮���� ��û ��
create table proxyApplyBoard(
  num integer unique not null, -- �Խñ� ��ȣ
    id varchar2(16) not null, -- �Խñ� ID
    nickname  varchar2(30) not null, -- �Խñ� �г���
    category varchar2(30) not null, -- ��ǰ ī�װ�
    title varchar2(150) not null, -- �Խñ� ����
    content varchar2(3000) not null, -- �Խñ� ����
    profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    productImg varchar2(550), -- �Խñ� �� �̹���
    process number(1) not null, -- ���� ���� ����
    registDay date default sysdate, -- �Խñ� ��ϳ�¥
    updateDay date default sysdate, -- �Խñ� ���� ��¥
    chkDay date default sysdate,
    ip varchar2(32), -- �Խñ� �ۼ� Ip
    URL varchar2(300)
);

-- �븮���� ������
create sequence applyNum NOCACHE NOCYCLE;

-- ���ƿ� ���̺�
create table likeTable(
likingId varchar2(16),
boardNum number,
boardTable varchar2(50)
);

-- ����Ȯ�� ���̺�
create table checkParticipate(
boardNum number,
tableName varchar2(20),
mem varchar2(20),
partiDate date,
status char(1),
paid char(1)
);

--feed ���� ���̺�
create table makeFeed(
id varchar(20),
feed1 varchar(50),
feed2 varchar(50),
feed3 varchar(50)
);

-- �������� ���̺�
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

--���� ��������
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
-- ������������ ������
create sequence sub_num nocache nocycle;


--�˻����α���Ʈ
create table searchKey(
keyWord varchar2(150),
tableName varchar2(30),
hit number
);

-- �����û ���̺�


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



-- ������ ����/����
create sequence bus_apply_num nocache nocycle;

create table friendTable(
followingId varchar2(30),
followedId varchar2(30)
);

select DISTINCT keyWord from ( select * from searchKey order by hit
		desc)
		where ROWNUM  <=
		25;