create table subScribe(
    sub_num integer unique not null,
    buyer_id varchar2(100),
    buyer_name varchar2(100),
    buyer_nickname varchar2(100),
    buyer_tel varchar2(100),
    buyer_email varchar2(100),
    schedules number,
    sub_uid varchar2(100),
    sub_customer_uid varchar2(100) unique not null,
    sub_method varchar2(100),
    sub_merchant_uid varchar2(100) unique not null,
    sub_name varchar2(100),
    sub_amount number,
    sub_date date default sysdate,
    sub_premium varchar2(100)
);
create sequence sub_num nocache nocycle;
drop sequence sub_num;
drop table subScribe;
select * from subScribe;
insert into subScribe values(sub_num.nextval,'buyer_id','buyer_name','buyer_nickname','buyer_tel','buyer_email',15,'sub_uid','sub_customer_uid','sub_method','sub_merchant_uid','sub_name',100,sysdate,'sub_premium');

commit;