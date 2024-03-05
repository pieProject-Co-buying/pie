
-- 채팅룸 생성
create table chatRoom (
  roomNumber NUMBER PRIMARY KEY,
  managerMemNum VARCHAR2(16),
  joinMemNum VARCHAR2(16),
  foreign key (managerMemNum) references member_user (id),
  foreign key (joinMemNum) references member_user (id)

)

-- 시퀀스 생성/삭제
create sequence roomNumber nocache nocycle;


-- 채팅 메세지 저장
create table pie_messages (
    roomName varchar(200) not null,
    roomNumber varchar(200) not null,
    sender_id varchar(200) not null,
    sender varchar(200) not null,
    message varchar(1000) not NULL,
    sendTime date default sysdate
);



create table pie_chatRoom (
    roomName varchar2(200) not null,
    roomNumber number unique not null,
    partyMem varchar2(40)
);







-- 시퀀스 생성/삭제
create sequence room_num nocache nocycle;
drop sequence room_num;

-- 테이블 삭제
drop table pie_messages;

-- 테이블 확인
select * from pie_messages;


commit;



-- 만드는중 ing
create table pie_chatRoom (
    roomName varchar2(200) not null,
    roomNumber number unique not null,
    partyMem varchar2(40)
);

create sequence roomNumber nocache nocycle;

insert into pie_chatroom values (1,1,'/효효/혜혜');
insert into pie_chatroom values (2,2,'/혜혜/후후');
insert into pie_chatroom values (3,3,'/장장/효효');

select * from pie_chatRoom where partyMem like '%/혜혜%' order by roomNumber asc ;

select * from pie_chatRoom;


drop table pie_chatRoom;

select * from pie_chatRoom;
commit;