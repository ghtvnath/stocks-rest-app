insert into T_STOCK (id,name,desc,current_price, created_ts, updated_ts) values(1,'VSA', 'Visa Worldwide Pte Ltd', 123.15,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());

insert into T_STOCK (id,name,desc,current_price, created_ts, updated_ts) values(2,'FBK', 'Facebook Incorporation',100.73,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (1, 1, 120.10, TO_TIMESTAMP('10-MAR-18 14:10:10.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (2, 1, 110.50, TO_TIMESTAMP('10-MAR-18 15:20:10.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (3, 1, 108.40, TO_TIMESTAMP('11-MAR-18 07:15:24.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (4, 1, 129.75, TO_TIMESTAMP('11-MAR-18 12:59:43.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (5, 1, 99.25, TO_TIMESTAMP('14-MAR-18 19:35:10.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (6, 1, 102.50, TO_TIMESTAMP('16-MAR-18 01:17:23.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (7, 2, 105.70, TO_TIMESTAMP('10-MAR-18 14:10:10.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (8, 2, 130.40, TO_TIMESTAMP('10-MAR-18 13:30:10.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (9, 2, 125.70, TO_TIMESTAMP('15-MAR-18 12:25:10.123000','DD-MON-RR HH24:MI:SS.FF'));

insert into T_STOCK_HISTORY(id,stock_id,history_price,price_ts) values (10, 2, 120.40, TO_TIMESTAMP('21-MAR-18 06:14:25.123000','DD-MON-RR HH24:MI:SS.FF'));