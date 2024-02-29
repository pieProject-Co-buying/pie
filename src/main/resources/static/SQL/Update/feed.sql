create table makeFeed(
id varchar(20),
feed1 varchar(50),
feed2 varchar(50),
feed3 varchar(50)
);

insert into makeFeed values ('@AnonymousUser_pie*' , 'none', 'none', 'none');
delete makeFeed where id = 'AnonymousUser_pie*';

select*from makeFeed;

commit;

select * from proxyBuyBoard;

update proxyBuyBoard set pr_process = 0 where pr_num = 16;