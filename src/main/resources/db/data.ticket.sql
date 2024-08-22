
truncate table Ticket cascade;
truncate table event_tickets cascade;
insert into Ticket(id ,price ,ticket_type ) values
 ('4cae3788-e52b-49c1-ba5e-b61bb7deda82',200,  'VIP'),
 ('4cae3788-e52b-49c1-ba5e-b61bb7deda83',200,  'V_VIP');



insert into event_tickets(event_id ,id) values
    ( 1,'4cae3788-e52b-49c1-ba5e-b61bb7deda82'),
    ( 1,'4cae3788-e52b-49c1-ba5e-b61bb7deda83');



