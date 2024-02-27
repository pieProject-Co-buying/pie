-- ���� �������� ����
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

-- ���� �������� ������
create sequence to_num nocache nocycle;

-- ���� �������� ���Թ�
insert into townBuyBoard values(
to_num.nextval,
'testID',
'testNickName',
'testCategory',
'1',
'�׽�Ʈ ����',
'�׽�Ʈ ����',
'default.png',
'product1.jpg',
0,
'#�׽�Ʈ#�Խñ�',
'����� �߱�',
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

-- ���� ��������  select
select * from townBuyBoard; -- �Խñ� ��ü ����
select * from townBuyBoard order by to_num desc; -- �Խñ� ��ü ���� (�ֽż� ����)
select * from ( select * from townBuyBoard order by to_registDay desc) where ROWNUM <= 3; -- �Խñ� ���� ���� ���

select * from townBuyBoard where to_num = 15; -- �Խñ� num ���� ����

-- �븮�������� update
update townBuyBoard set to_nickname='�����г���', to_category='����ī�װ�', to_title='��������', to_content='��������', to_profileImg='update.png', to_productImg='product2.jpg', to_tag='#����#�Խñ�', 
to_deadLine=TO_DATE('20240307', 'YYYYMMDD'), to_personnelMax=6 where to_num=15; -- ��ü ������Ʈ
update townBuyBoard set to_hit = to_hit+1 where to_num = 15; -- ��ȸ�� ���

-- �븮�������� delete
delete townBuyBoard where to_num = 15; -- �Խñ� num ���� ����

-- ���̺� ����
drop table townBuyBoard;