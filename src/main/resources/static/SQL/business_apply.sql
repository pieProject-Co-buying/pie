
CREATE TABLE businessApplyForm (
    bus_apply_num NUMBER UNIQUE NOT NULL,
    
    bus_title VARCHAR2(100) NOT NULL,
    bus_content VARCHAR2(2000) NOT NULL,
    bus_img VARCHAR2(200),
    
    bus_name VARCHAR2(100) NOT NULL,
    bus_num VARCHAR2(100) NOT NULL,
    bus_postCode VARCHAR2(300),
    bus_address_main VARCHAR2(300),    
    bus_address_sub VARCHAR2(300),   
    bus_productName VARCHAR2(100) NOT NULL,   
    bus_Maxqnt NUMBER NOT NULL,
    bus_unitPrice NUMBER NOT NULL,
 
    bus_chargePerson VARCHAR2(100) NOT NULL,
    bus_phone VARCHAR2(100) NOT NULL,
    bus_email VARCHAR2(100) NOT NULL,
    
    bus_password VARCHAR2(30) NOT NULL,
    
    bus_hit NUMBER,
    bus_writeDay DATE DEFAULT SYSDATE
);


insert into businessApplyForm values (
		bus_apply_num.nextval, 
        
		'제목',
		'내용',
		'product1.jpg',
		'회사이름',
		'사업자번호',
		'우편번호',
        '메인주소',
        '서브주소',
		'제품이름',
		'100',
		'100000',
		'담당자',
		'010-2222-2222',      
 		'111@1.com',
        '12345',
        0,
		sysdate
		
);

select * from businessApplyForm;
drop table businessApplyForm;