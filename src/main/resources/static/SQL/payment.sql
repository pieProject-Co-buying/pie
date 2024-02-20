create table payment(
    buyer_name varchar2(100),
    buyer_nickname varchar2(100),
    buyer_tel varchar2(100),
    buyer_addr varchar2(100),
    buyer_email varchar2(100),
    buyer_postcode varchar2(100),
    pay_uid varchar2(100),
    pay_method varchar2(100),
    pay_merchant_uid varchar2(100),
    pay_name varchar2(100),
    pay_amount number,
    pay_date date default sysdate
);
--테이블 관련
drop table payment;
--insert
insert into payment (buyer_name,buyer_nickname,buyer_tel,buyer_addr,buyer_email,buyer_postcode,pay_uid, pay_method, pay_merchant_uid, pay_name, pay_amount, pay_date) VALUES ('w','w','w','w','w','w','123','123','123','123',123,sysdate);
--select
select * from payment;
select * from payment where pay_method='카드결제';
--commit
commit;