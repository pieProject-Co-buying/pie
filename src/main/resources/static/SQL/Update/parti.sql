create table checkParticipate(
boardNum number,
tableName varchar2(20),
mem varchar2(20),
partiDate date,
status char(1),
paid char(1)
);

select*from checkParticipate;

DELETE FROM checkParticipate a
WHERE ROWID > (
    SELECT MIN(b.ROWID)
    FROM checkParticipate b
    WHERE a.boardNum = b.boardNum
);