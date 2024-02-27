create table searchKey(
keyWord varchar2(150),
tableName varchar2(30),
hit number
);

select * from searchKey;

select keyWord from ( select * from searchKey where tableName='townBuy' order by hit desc)
		where ROWNUM < 5;
        
create table feed{
id varchar2(20);

}