-- townBoard ����         
create table townBuyBoard (
    to_num integer unique not null, -- �Խñ� ��ȣ
    to_id varchar2(16) not null, -- �Խñ� ID
    to_nickname  varchar2(30) not null, -- �Խñ� �г���
    to_category varchar2(30) not null, -- ��ǰ ī�װ�
    to_premium char(1), -- �����̾� ȸ�� ����
    to_title varchar2(150) not null, -- �Խñ� ����
    to_content varchar2(3000) not null, -- �Խñ� ����
    to_profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    to_productImg varchar2(550), -- �Խñ� �� �̹���
    to_hit number, -- ��ȸ�� �߰�
    to_tag varchar2(50), -- �Խñ� �±�
    to_address varchar2(150), -- �ۼ��� �ּ�
    to_process char(1), -- ���� ���� ����
    to_registDay date default sysdate, -- �Խñ� ��ϳ�¥
    to_updateDay date default sysdate, -- �Խñ� ���� ��¥
    to_deadLine date, -- ���� ��������
    to_personnelMax integer not null, -- �ִ� �����ο�
    to_personnelNow integer, -- ���� �����ο�
    to_priceTotal number, -- ���� ����
    to_pricePer number, -- �δ� ����
    to_ip varchar2(32), -- �Խñ� �ۼ� Ip
    to_like number -- �Խñ� ���ƿ�
);

-- townBoard ������ ����
create sequence to_num nocache nocycle;

-- �븮�������� ����
create table proxyBuyBoard (
    pr_num integer unique not null, -- �Խñ� ��ȣ
    pr_id varchar2(16) not null, -- �Խñ� ID
    pr_nickname  varchar2(30) not null, -- �Խñ� �г���
    pr_category varchar2(30) not null, -- ��ǰ ī�װ�
    pr_title varchar2(150) not null, -- �Խñ� ����
    pr_content varchar2(3000) not null, -- �Խñ� ����
    pr_profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    pr_productImg varchar2(550), -- �Խñ� �� �̹���
    pr_hit number default 0, -- ��ȸ�� �߰�
    pr_tag varchar2(50), -- �Խñ� �±�
    pr_process char(1), -- ���� ���� ����
    pr_registDay date default sysdate, -- �Խñ� ��ϳ�¥
    pr_updateDay date default sysdate, -- �Խñ� ���� ��¥
    pr_deadLine date, -- ���� ��������
    pr_personnelMax integer not null, -- �ִ� �����ο�
    pr_personnelNow integer, -- ���� �����ο�
    pr_priceTotal number, -- ���� ����
    pr_pricePer number, -- �δ� ����
    pr_ip varchar2(32), -- �Խñ� �ۼ� Ip
    pr_like number -- �Խñ� ���ƿ�
);

-- �븮�������� ������
create sequence pr_num nocache nocycle;

-- ���� ���� ����
create table shareServiceBoard (
    sh_num integer not null, -- �Խñ� ��ȣ
    sh_id varchar2(16) not null, -- �Խñ� ID
    sh_nickname  varchar2(30) not null, -- �Խñ� �г���
    sh_category varchar2(30) not null, -- ��ǰ ī�װ�
    sh_premium char(1), -- �����̾� ȸ�� ����
    sh_title varchar2(150) not null, -- �Խñ� ����
    sh_content varchar2(3000) not null, -- �Խñ� ����
    sh_profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    sh_productImg varchar2(550), -- �Խñ� �� �̹���
    sh_hit number, -- ��ȸ�� �߰�
    sh_tag varchar2(50), -- �Խñ� �±�
    sh_process char(1), -- ���� ���� ����
    sh_registDay date default sysdate, -- �Խñ� ��ϳ�¥
    sh_updateDay date default sysdate, -- �Խñ� ���� ��¥
    sh_deadLine date, -- ���� ��������
    sh_personnelMax integer not null, -- �ִ� �����ο�
    sh_personnelNow integer, -- ���� �����ο�
    sh_priceTotal number, -- ���� ����
    sh_pricePer number, -- �δ� ����
    sh_ip varchar2(32), -- �Խñ� �ۼ� Ip
    sh_like number, -- �Խñ� ���ƿ�
    sh_product varchar2(30) -- ��ǰ�ѹ�
);

-- ���� ���� ������
create sequence sh_num nocache nocycle;

-- �븮���� ��û ��
create table proxyApplyBoard(
  pr_num integer unique not null, -- �Խñ� ��ȣ
    pr_id varchar2(16) not null, -- �Խñ� ID
    pr_nickname  varchar2(30) not null, -- �Խñ� �г���
    pr_category varchar2(30) not null, -- ��ǰ ī�װ�
    pr_title varchar2(150) not null, -- �Խñ� ����
    pr_content varchar2(3000) not null, -- �Խñ� ����
    pr_profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    pr_productImg varchar2(550), -- �Խñ� �� �̹���
    pr_process number(1) not null, -- ���� ���� ����
    pr_registDay date default sysdate, -- �Խñ� ��ϳ�¥
    pr_updateDay date default sysdate, -- �Խñ� ���� ��¥
    pr_chkDay date default sysdate,
    pr_ip varchar2(32), -- �Խñ� �ۼ� Ip
    pr_URL varchar2(300)
);

-- �븮���� ������
create sequence pr_applyNum NOCACHE NOCYCLE;

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
    bus_writeDay DATE DEFAULT SYSDATE
);

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