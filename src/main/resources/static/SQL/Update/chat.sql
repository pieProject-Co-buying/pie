-- 채팅방 대화내용 저장할 테이블
create table pie_messages (
    roomName varchar(200) not null,
    roomNumber varchar(200) not null,
    sender_id varchar(200) not null,
    sender varchar(200) not null,
    message varchar(1000) not NULL,
    sendTime date default sysdate
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
    roomName varchar(200) not null,
    roomNumber varchar(200) not null
);
