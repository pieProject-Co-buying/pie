create table proxyApplyBoard(
  pr_num integer unique not null, -- 게시글 번호
    pr_id varchar2(16) not null, -- 게시글 ID
    pr_nickname  varchar2(30) not null, -- 게시글 닉네임
    pr_category varchar2(30) not null, -- 물품 카테고리
    pr_title varchar2(150) not null, -- 게시글 제목
    pr_content varchar2(3000) not null, -- 게시글 내용
    pr_profileImg varchar2(50), -- 게시글 작성자 프로필 이미지
    pr_productImg varchar2(550), -- 게시글 내 이미지
    pr_process number(1) not null, -- 모집 진행 여부
    pr_registDay date default sysdate, -- 게시글 등록날짜
    pr_updateDay date default sysdate, -- 게시글 수정 날짜
    pr_chkDay date default sysdate,
    pr_ip varchar2(32), -- 게시글 작성 Ip
    pr_URL varchar2(300)
);

update proxyApplyBoard set pr_process=3, pr_chkDay = sysdate where pr_num=29;

insert into proxyApplyBoard values(
		pr_applyNum.nextval,
		'test',
		'test',
		'baby',
		'test',
		'test',
		'default.png',
		'proxy_title2-01.png/',
		0,
		sysdate,
		sysdate,
		sysdate,
		'0.0.0.0.0',
		'http://www.naver.com'
		);

update proxyapplyboard set pr_title = 'new table' where pr_num = 18;

drop table proxyApplyBoard;

create sequence pr_applyNum NOCACHE NOCYCLE;

select * from ( select * from proxyBuyBoard order by pr_like desc, pr_hit desc)
		where ROWNUM <= 5;

select * from proxyApplyBoard;

commit;