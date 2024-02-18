-- 좋아요 테이블
create table likeTable(
likingId varchar2(16),
boardNum number,
boardTable varchar2(50)
);

-- 좋아요 테이블 삽입문
insert into likeTable values ('testId', 1, 'proxyBuyBoard');

-- 좋아요 테이블 delete
delete likeTable where likingId = 'testId' and boardNum = 1 and boardTable='proxyBuyBoard';

-- 테이블 삭제
drop table likeTable;