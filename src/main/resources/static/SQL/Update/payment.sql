------------------------------------------
create table payment(
    pay_num integer not null,
    buyer_id varchar2(100),
    buyer_name varchar2(100),
    buyer_nickname varchar2(100),
    buyer_tel varchar2(100),
    buyer_addr varchar2(100),
    buyer_email varchar2(100),
    buyer_postcode varchar2(100),
    pay_uid varchar2(100),
    pay_method varchar2(100),
    pay_merchant_uid varchar2(100) unique not null,
    pay_name varchar2(100),
    pay_amount number,
    pay_date date default sysdate,
    pay_category varchar2(100),
    pay_refund number default 0
);
--테이블 관련
drop table payment;
--insert
insert into payment (pay_num,buyer_id,buyer_name,buyer_nickname,buyer_tel,buyer_addr,buyer_email,buyer_postcode,pay_uid, pay_method, pay_merchant_uid, pay_name, pay_amount, pay_date,pay_category) VALUES (pay_num.nextval,'w','w','w','w','w','w','w','123','123','123','123',123,sysdate,'Share');
--select
select * from payment;
select * from payment where buyer_name='admin' order by pay_num desc;
select pay_method from payment where pay_num = 1 and pay_category = 'Share' and pay_merchant_uid=123;
select pay_date from payment where pay_num = 1 and pay_category = 'Share';
update shareServiceBoard set sh_personnelNow = sh_personnelNow + 1 where sh_num = 1;
--select payment from pay_merchant_uid where pay_merchant_uid=;
select pay_merchant_uid from payment where pay_merchant_uid=173513486;
select * from payment where pay_merchant_uid=173513486;
--delete
delete payment where pay_num=1;
--commit
commit;
rollback;