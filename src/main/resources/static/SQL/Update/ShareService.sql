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
drop sequence sh_num;

-- ���� ���� ���Թ�
insert into shareServiceBoard values(
sh_num.nextval,
'admin',
'testNickName',
'testCategory',
'1',
'�׽�Ʈ ����1',
'�׽�Ʈ ����',
'boog.png',
'product1.jpg/',
0,
'#�׽�Ʈ#�Խñ�',
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

-- ���� ����  select
select * from shareServiceBoard; -- �Խñ� ��ü ����
select * from shareServiceBoard order by sh_num desc; -- �Խñ� ��ü ���� (�ֽż� ����)
select * from ( select * from shareServiceBoard order by sh_registDay desc) where ROWNUM <= 3; -- �Խñ� ���� ���� ���

select * from shareServiceBoard where sh_num = 15; -- �Խñ� num ���� ����
update shareServiceBoard set sh_process = '0' where sh_num = 1;
drop table shareServiceBoard;
commit;