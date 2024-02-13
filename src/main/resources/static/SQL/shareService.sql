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
insert into shareServiceBoard values (sh_numID.nextval,'admin1','닉네임1','넷플릭스 공구 모집','넷플릭스 공구 모집합니다.','OTT',90000,'진행중',TO_DATE('2024-02-08', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,10,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin2','닉네임2','왓챠 공구 모집','왓챠 공구 모집합니다.','OTT',80000,'진행중',TO_DATE('2024-02-03', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,1,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin3','닉네임3','쿠팡플레이 공구 모집','쿠팡플레이 공구 모집합니다.','OTT',70000,'진행중',TO_DATE('2024-02-05', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,3,'486.486.486.6');
insert into shareServiceBoard values (sh_numID.nextval,'admin4','닉네임4','디즈니 공구 모집','디즈니 공구 모집합니다.','OTT',130000,'진행중',TO_DATE('2024-02-06', 'YYYY-MM-DD'),sysdate,TO_DATE('2024-02-25', 'YYYY-MM-DD'),12,8,'486.486.486.6');
-----select-----
select * from shareServiceBoard where sh_numID=1;
-------------------------------------------------