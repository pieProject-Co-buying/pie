-- ���ƿ� ���̺�
create table likeTable(
likingId varchar2(16),
boardNum number,
boardTable varchar2(50)
);

-- ���ƿ� ���̺� ���Թ�
insert into likeTable values ('testId', 1, 'proxyBuyBoard');

-- ���ƿ� ���̺� delete
delete likeTable where likingId = 'testId' and boardNum = 1 and boardTable='proxyBuyBoard';

-- ���̺� ����
drop table likeTable;