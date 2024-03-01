
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


CREATE TABLE businessApplyForm (
    bus_apply_num NUMBER UNIQUE NOT NULL,
    
    bus_title VARCHAR2(100) NOT NULL,
    bus_content VARCHAR2(2000) NOT NULL,
    bus_img VARCHAR2(2000),
    
    bus_name VARCHAR2(100) NOT NULL,
    bus_num VARCHAR2(100) NOT NULL,
    postCode VARCHAR2(300),
    address_main VARCHAR2(300),    
    address_sub VARCHAR2(300),   
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
        
		'? λͺ?',
		'?΄?©',
		'product1.jpg',
		'??¬?΄λ¦?',
		'?¬??λ²νΈ',
		'?°?Έλ²νΈ',
        'λ©μΈμ£Όμ',
        '?λΈμ£Ό?',
		'? ??΄λ¦?',
		'100',
		'100000',
		'?΄?Ή?',
		'010-2222-2222',      
 		'111@1.com',
        '12345',
        0,
		sysdate
		
);

select * from businessApplyForm;
drop table businessApplyForm;