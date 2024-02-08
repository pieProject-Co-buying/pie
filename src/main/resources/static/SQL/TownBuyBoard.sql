
-- townBoard 생성         
                   

public class TownBuyBoardDto {
	
	private String to_num;
	private String to_id;
	private String to_nickname;
	private String to_premium;
	private String to_category;
	private String to_title;
	private String to_content;
	private String to_profileImg;
	private String to_productImg;
	private Integer to_hit;
	private String to_tag;
	private String to_address;
	private String to_process;
	private String to_registDay;
	private String to_updateDay;
	private Integer to_deadString;
	private Integer to_personnelMax;
	private String to_personnelNow;
	private String to_ip;
	
}



-- 테이블 삭제
drop table townBuyBoard purge;

-- 시퀀스
-- 시퀀스 생성
create sequence to_num nocache nocycle;
-- 시퀀스 삭제
drop sequence to_num;

--커밋
commit;

-- 데이터 입력
insert into townBuyBoard values(
mem_seq.nextval,
'soloKim',
'soloKim',
'육아',
'1',
'감자 같이 사실분?',
'너무 많아서 같이 사고 싶어용~',
'person1.jpg',
'product1.jpg',
0,
'감자',
'서울시 중구',
'1',
sysdate,
sysdate,
TO_DATE('20240210', 'YYYYMMDD'),
5,
2,
'123'
);


-- 전체 테이블 보기
select * from townBuyBoard;


select to_title, to_content from townBuyBoard where to_num = ;




















-- member_user : 회원 데이터 베이스
create table member_user
(
    mem_num integer unique not null,
    id       varchar2(16) primary key,
    password varchar2(20) not null,
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
    friends varchar2(620)
);

create sequence mem_seq nocache nocycle;

-- insert
insert into member_user
values (mem_seq.nextval,
        'admin',
        'admin',
        '관리자',
        '관리자',
        '선택안함',
        'default.png',
        'admin@email.com',
        '010-1111-1111',
        '00000',
        '서울특별시 중구 세종대로 110',
        '000호',
        '1',
        ''
        );
        
-- select
select*from member_user;

-- select_by ID
select*from member_user where id = 'id';

--delete_by ID;
select*from member_user where id = 'id';

--update_by ID;
update member_user set mem_num='', 
                       id='', 
                       password='', 
                       name='', 
                       nickname='', 
                       gender='', 
                       email='', 
                       phone='', 
                       postCode='', 
                       address_main='',
                       address_sub='', 
                       friends='' 
                   where id = 'id';
                   


