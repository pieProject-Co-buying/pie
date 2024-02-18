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

-- �븮�������� ���Թ�
insert into proxyBuyBoard values(
pr_num.nextval,
'testID',
'testNickName',
'testCategory',
'�׽�Ʈ ����',
'�׽�Ʈ ����',
'default.png',
'product1.jpg',
0,
'#�׽�Ʈ#�Խñ�',
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

-- �븮�������� select
select * from proxyBuyBoard; -- �Խñ� ��ü ����
select * from proxyBuyBoard order by pr_num desc; -- �Խñ� ��ü ���� (�ֽż� ����)
select * from ( select * from proxyBuyBoard order by pr_registDay desc) where ROWNUM <= 3; -- �Խñ� ���� ���� ���

select * from proxyBuyBoard where pr_num = 15; -- �Խñ� num ���� ����

-- �븮�������� update
update proxybuyboard set pr_nickname='�����г���', pr_category='����ī�װ�', pr_title='��������', pr_content='��������', pr_profileImg='update.png', pr_productImg='product2.jpg', pr_tag='#����#�Խñ�', 
pr_deadLine=TO_DATE('20240307', 'YYYYMMDD'), pr_personnelMax=6 where pr_num=15; -- ��ü ������Ʈ
update proxyBuyBoard set pr_hit = pr_hit+1 where pr_num = 15; -- ��ȸ�� ���

-- �븮�������� delete
delete proxyBuyBoard where pr_num = 15; -- �Խñ� num ���� ����

-- ���̺�&������ ����
drop table proxyBuyBoard;
drop SEQUENCE pr_num;