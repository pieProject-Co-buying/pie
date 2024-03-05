
-- ä�÷� ����
create table chatRoom (
  roomNumber NUMBER PRIMARY KEY,
  managerMemNum VARCHAR2(16),
  joinMemNum VARCHAR2(16),
  foreign key (managerMemNum) references member_user (id),
  foreign key (joinMemNum) references member_user (id)

)

-- ������ ����/����
create sequence roomNumber nocache nocycle;


-- ä�� �޼��� ����
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







-- ������ ����/����
create sequence room_num nocache nocycle;
drop sequence room_num;

-- ���̺� ����
drop table pie_messages;

-- ���̺� Ȯ��
select * from pie_messages;


commit;



-- ������� ing
create table pie_chatRoom (
    roomName varchar2(200) not null,
    roomNumber number unique not null,
    partyMem varchar2(40)
);

create sequence roomNumber nocache nocycle;

insert into pie_chatroom values (1,1,'/ȿȿ/����');
insert into pie_chatroom values (2,2,'/����/����');
insert into pie_chatroom values (3,3,'/����/ȿȿ');

select * from pie_chatRoom where partyMem like '%/����%' order by roomNumber asc ;

select * from pie_chatRoom;


drop table pie_chatRoom;

select * from pie_chatRoom;
commit;