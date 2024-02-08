create table shareServiceBoard(
    sh_numID number primary key,
    sh_id varchar2(100) not null,--
    sh_nickname varchar2(100) not null,
    sh_title varchar2(100) not null,
    sh_content varchar2(100) not null,
    sh_category varchar2(100) not null,
    sh_price integer not null,
    sh_process varchar2(10) not null,
    sh_registDay date,
    sh_updateDay date default sysdate,
    sh_DeadLine date not null,
    sh_personnelMax integer,
    sh_personnelNow integer,
    sh_ip varchar2(32) not null--
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
insert into shareServiceBoard values (sh_numID.nextval,'admin1','�г���1','���ø��� ���� ����','���ø��� ���� �����մϴ�.','OTT',90000,'������',TO_DATE('2024-02-08', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,10,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin2','�г���2','��í ���� ����','��í ���� �����մϴ�.','OTT',80000,'������',TO_DATE('2024-02-03', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,1,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin3','�г���3','�����÷��� ���� ����','�����÷��� ���� �����մϴ�.','OTT',70000,'������',TO_DATE('2024-02-05', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,3,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin4','�г���4','����� ���� ����','����� ���� �����մϴ�.','OTT',130000,'������',TO_DATE('2024-02-06', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,8,'486.486.486.6');
-----select-----
select * from shareServiceBoard where sh_numID=1;
-------------------------------------------------