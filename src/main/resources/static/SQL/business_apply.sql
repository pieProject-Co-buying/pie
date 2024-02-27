CREATE TABLE businessApplyForm (
    bus_apply_num NUMBER UNIQUE NOT NULL,
    bus_name VARCHAR2(100) NOT NULL,
    bus_num VARCHAR2(100) NOT NULL,
    bus_address VARCHAR2(300),
    bus_Maxqnt NUMBER NOT NULL,
    bus_unitPrice NUMBER NOT NULL,
    bus_chargePerson VARCHAR2(100) NOT NULL,
    bus_img VARCHAR2(1000),
    bus_password varchar2(30) not null,
    bus_hit NUMBER
);