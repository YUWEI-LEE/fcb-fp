
INSERT INTO FPCUSTOMER(FPC_ACCOUNT,FPC_CUSTOMER_ID,FPC_STATUS,FPC_VALID_CRCY_CNT,FPC_BOOK_TYPE) VALUES('09340123456','A1234567890','00','02','NONE');
INSERT INTO FPMASTER(FPC_LINK_ID,FPM_CRCY, FPM_STATUS, FPM_BAL) VALUES(1,'USD','00', 50000.00);
INSERT INTO FPMASTER(FPC_LINK_ID,FPM_CRCY, FPM_STATUS, FPM_BAL) VALUES(1,'CNY','00', 260000.00);
INSERT INTO FPCUSTOMER(FPC_ACCOUNT,FPC_CUSTOMER_ID,FPC_STATUS,FPC_VALID_CRCY_CNT,FPC_BOOK_TYPE) VALUES('11168123456','B1234567890','00','01','MULTI');
INSERT INTO FPMASTER(FPC_LINK_ID,FPM_CRCY, FPM_STATUS, FPM_BAL) VALUES(2,'JPY','00', 7837340.00);

;