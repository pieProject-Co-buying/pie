-- ä�ù� ��ȭ���� ������ ���̺�
create table pie_messages (
    roomName varchar(200) not null,
    roomNumber varchar(200) not null,
    sender_id varchar(200) not null,
    sender varchar(200) not null,
    message varchar(1000) not NULL,
    sendTime date default sysdate
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
    roomName varchar(200) not null,
    roomNumber varchar(200) not null
);
