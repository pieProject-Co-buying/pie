create table shareServiceBoard(
    sh_numID number primary key,
    sh_id varchar2(100) not null,--
    sh_nickname varchar2(100) not null,
    sh_title varchar2(100) not null,
    sh_content varchar2(3000) not null,
    sh_category varchar2(100) not null,
    sh_price integer not null,
    sh_filename varchar2(300),
    sh_process varchar2(30) not null,
    sh_registDay date default sysdate,
    sh_updateDay date default sysdate,
    sh_DeadLine date not null,
    sh_personnelMax integer,
    sh_personnelNow integer,
    sh_ip varchar2(32) --not null--
);
-----���̺� ����-----
drop table shareServiceBoard;
-----���̺� ��ȸ-----
select * from shareServiceBoard;
-----Ŀ��-----
commit;
-----������ ���� �� ����-----
create sequence sh_numID nocycle nocache;
drop sequence sh_numID;
-----insert-----
insert into shareServiceBoard values (sh_numID.nextval,'admin','admin','test ���� ����','���ø��� ���� �����մϴ�.','OTT',90000,'potato.png','������',TO_DATE('2024-02-08', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,10,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin2','�г���2','���� ���� ����','���� ���� �����մϴ�.','����',80000,'potato.png','������',TO_DATE('2024-02-03', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,1,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin3','�г���3','�����÷��� ���� ����','�����÷��� ���� �����մϴ�.','OTT',70000,'potato.png','������',TO_DATE('2024-02-05', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,3,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin4','�г���4','����å ���� ����','����å ���� �����մϴ�.','����/����',130000,'potato.png','������',TO_DATE('2024-02-06', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,8,'486.486.486.6');
insert into shareServiceBoard (sh_numID,sh_id,sh_nickname,sh_title,sh_content,sh_category,sh_price,sh_process,sh_DeadLine,sh_personnelMax,sh_filename) values (sh_numID.nextval,'id','nickname','title','content','category',100,'������',TO_DATE('2024-02-19', 'YYYY-MM-DD'),10,'potato.png');
-----select-----
select * from shareServiceBoard where sh_numID=1;
select * from shareServiceBoard where sh_id=123;
-----update-----
update shareServiceBoard set sh_title='Ÿ��Ʋ',sh_content='����',sh_price='3000',sh_personnelMax=10,sh_DeadLine=TO_DATE('2024-02-05', 'YYYY-MM-DD') where sh_id='admin';
delete shareServiceBoard where sh_id='admin';
-----select-----
select * from shareServiceBoard where sh_title like '%��%';
-------------------------------------------------