
-- townBoard ����         
                   
create table townBuyBoard (
    to_num integer unique not null, -- �Խñ� ��ȣ
    to_id varchar2(16),
    to_nickname  varchar2(30),
    to_category varchar2(30) not null, -- ��ǰ ī�װ�
    to_premium char(1), -- �����̾� ȸ�� ����
    to_title varchar2(150) not null,
    to_content varchar2(3000) not null,
    to_price varchar2(100) not null,
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




-- ������
-- ������ ����
create sequence to_num nocache nocycle;
-- ������ ����
drop sequence to_num;


-- ���̺� ����
drop table townBuyBoard purge;






-- ########### ������ ���� ###########

update townBuyBoard 
set to_category = 'food', 
    to_title = '���ڰ� �ƴ϶� ī�޶󿴳׿� �˼��˼�',
    to_content = '���ڰ� �ƴ϶� ī�޶󿴳׿� �˼��˼�',
    to_price = '500000000000', 
    to_personnelMax = '100',  
    to_deadLine = TO_DATE('2024-02-15', 'YYYY-MM-DD') 
where to_num = 9;


commit;



--Ŀ��
commit;



-- ############## ������ �Է� ##############
insert into townBuyBoard values(
mem_seq.nextval,
'soloKim',
'soloKim',
'����',
'1',
'���� ���� ��Ǻ�?',
'�ʹ� ���Ƽ� ���� ��� �;��~',
'4000',
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


insert into townBuyBoard (to_num, to_category, to_title, to_content, to_price, to_personnelMax, to_deadLine) values(mem_seq.nextval, 'baby', '���� �Ⱦƿ�', '���� ���ƿ� ���ڰ���', '4000', '8', TO_DATE('20240210', 'YYYYMMDD'));

insert into townBuyBoard (to_num, to_category, to_title, to_content, to_price, to_personnelMax, to_deadLine) values(to_num.nextval, 'baby', '���� �Ⱦƿ�', '���� ���ƿ� ���ڰ���', '4000', '8', TO_DATE('2024-02-10', 'YYYY-MM-DD'));



-- ############## �������� ������ ���� ##############

delete townBuyBoard where to_num = 63;





-- ��ü ���̺� ����
select * from townBuyBoard;

-- ������������ ����(������ ����)
select * from townBuyBoard order by to_num desc;

-- ������(1) �� ���� ī�װ� ����
select to_title, to_content, to_productImg, to_profileImg from townBuyBoard where to_process = 1 and to_category= '����';

select to_title, to_content, to_productImg, to_profileImg from townBuyBoard where to_process = 1 and to_category= '����';

-- �Խñ� ��ȣ �������� ������, ī�װ� 
select to_title, to_content, to_productImg, to_profileImg from townBuyBoard where to_title = '�Ʊ��';


select to_title, to_content, to_productImg, to_profileImg
from townBuyBoard
where to_title like '%'||#{param1}||'%' OR to_content LIKE '%'||#{param1}||'%';


-- �˻��� �Է� 
SELECT to_title, to_content, to_productImg, to_profileImg FROM townBuyBoard WHERE to_title LIKE '%����%' OR to_content LIKE '%����%';


-- �Խñ� ��ȣ �������� ������, ī�װ� 
select to_title, to_content, to_productImg, to_profileImg from townBuyBoard where to_num = 50 and to_process = 1 and to_category='����';

select * from townBuyBoard where to_num = '119';


select * from townBuyBoard where to_category = '����';









commit;



commit;


