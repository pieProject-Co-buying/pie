
-- townBoard ����         
                   
create table townBuyBoard (
    to_num integer unique not null, -- �Խñ� ��ȣ
    to_id varchar2(16) not null,
    to_nickname  varchar2(30) not null,
    to_category varchar2(30) not null, -- ��ǰ ī�װ�
    to_premium char(1), -- �����̾� ȸ�� ����
    to_title varchar2(150) not null,
    to_content varchar2(3000) not null,
    to_profileImg varchar2(50), -- �̹��� �߰�
    to_productImg varchar2(50), -- �̹��� �߰�
    to_hit number, -- ��ȸ�� �߰�
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



-- ���̺� ����
drop table townBuyBoard purge;

-- ������
-- ������ ����
create sequence to_num nocache nocycle;
-- ������ ����
drop sequence to_num;

--Ŀ��
commit;

-- ������ �Է�
insert into townBuyBoard values(
mem_seq.nextval,
'soloKim',
'soloKim',
'����',
'1',
'���� ���� ��Ǻ�?',
'�ʹ� ���Ƽ� ���� ��� �;��~',
'person1.jpg',
'product1.jpg',
0,
'����',
'����� �߱�',
'1',
sysdate,
sysdate,
TO_DATE('20240210', 'YYYYMMDD'),
5,
2,
'123'
);


-- ��ü ���̺� ����
select * from townBuyBoard;


select to_title, to_content from townBuyBoard where to_num = ;




















-- member_user : ȸ�� ������ ���̽�
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
        '������',
        '������',
        '���þ���',
        'default.png',
        'admin@email.com',
        '010-1111-1111',
        '00000',
        '����Ư���� �߱� ������� 110',
        '000ȣ',
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
                   


