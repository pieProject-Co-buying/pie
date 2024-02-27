create table proxyApplyBoard(
  pr_num integer unique not null, -- �Խñ� ��ȣ
    pr_id varchar2(16) not null, -- �Խñ� ID
    pr_nickname  varchar2(30) not null, -- �Խñ� �г���
    pr_category varchar2(30) not null, -- ��ǰ ī�װ�
    pr_title varchar2(150) not null, -- �Խñ� ����
    pr_content varchar2(3000) not null, -- �Խñ� ����
    pr_profileImg varchar2(50), -- �Խñ� �ۼ��� ������ �̹���
    pr_productImg varchar2(550), -- �Խñ� �� �̹���
    pr_process number(1) not null, -- ���� ���� ����
    pr_registDay date default sysdate, -- �Խñ� ��ϳ�¥
    pr_updateDay date default sysdate, -- �Խñ� ���� ��¥
    pr_chkDay date default sysdate,
    pr_ip varchar2(32), -- �Խñ� �ۼ� Ip
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