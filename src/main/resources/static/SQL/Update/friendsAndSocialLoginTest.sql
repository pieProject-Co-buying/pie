create table friendTable(
followingId varchar2(30),
followedId varchar2(30)
);

select * from friendtable;

drop table friendTable;

commit;

SELECT member_user.nickname
		FROM friendTable
		JOIN member_user ON friendTable.followedId = member_user.id;
        
SELECT member_user.nickname
FROM friendTable
JOIN member_user ON friendTable.followedId = member_user.id
WHERE friendTable.followingid = 'test';

-- member_user : 회원 데이터 베이스
create table socialmember_user
(
    mem_num integer unique not null,
    id varchar2(200) not null,
    name     varchar2(51) not null,
    nickname varchar2(24) not null,
    gender varchar2(12) not null,
    profile_pic varchar2(500) default 'default.png',
    email varchar2(50),
    phone varchar2(13),
    postCode varchar2(6),
    address_main varchar2(100),
    address_sub varchar2(200),
    agreement char(1) not null,
    friends varchar2(620),
    premium varchar2(4) default 'none',
    preDate date default sysdate,
    preEndDate date default sysdate+30
);