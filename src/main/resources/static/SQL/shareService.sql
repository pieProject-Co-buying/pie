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
-----테이블 삭제-----
drop table shareServiceBoard;
-----테이블 조회-----
select * from shareServiceBoard;
-----커밋-----
commit;
-----시퀀스 생성 및 삭제-----
create sequence sh_numID nocycle nocache;
drop sequence sh_numID;
-----insert-----
insert into shareServiceBoard values (sh_numID.nextval,'admin','admin','test 공구 모집','넷플릭스 공구 모집합니다.','OTT',90000,'potato.png','진행중',TO_DATE('2024-02-08', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,10,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin2','닉네임2','게임 공구 모집','게임 공구 모집합니다.','게임',80000,'potato.png','진행중',TO_DATE('2024-02-03', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,1,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin3','닉네임3','쿠팡플레이 공구 모집','쿠팡플레이 공구 모집합니다.','OTT',70000,'potato.png','진행중',TO_DATE('2024-02-05', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,3,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin4','닉네임4','음악책 공구 모집','음악책 공구 모집합니다.','도서/음악',130000,'potato.png','진행중',TO_DATE('2024-02-06', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,8,'486.486.486.6');
insert into shareServiceBoard (sh_numID,sh_id,sh_nickname,sh_title,sh_content,sh_category,sh_price,sh_process,sh_DeadLine,sh_personnelMax,sh_filename) values (sh_numID.nextval,'id','nickname','title','content','category',100,'진행중',TO_DATE('2024-02-19', 'YYYY-MM-DD'),10,'potato.png');
-----select-----
select * from shareServiceBoard where sh_numID=1;
select * from shareServiceBoard where sh_id=123;
-----update-----
update shareServiceBoard set sh_title='타이틀',sh_content='내용',sh_price='3000',sh_personnelMax=10,sh_DeadLine=TO_DATE('2024-02-05', 'YYYY-MM-DD') where sh_id='admin';
delete shareServiceBoard where sh_id='admin';
-----select-----
select * from shareServiceBoard where sh_title like '%음%';
-------------------------------------------------