create database banking_system_db;

insert into banking_system_db.admin (id, creation_date, last_update, name, password, role, username)
values  (1, '2022-05-21 08:12:03.912254', '2022-05-21 08:12:03.912254', 'Admincito 1', '$2a$10$PEP6gG91sglYPro2qS.Dse3eZOCTtjXYg0DT5mwLfoMEoTFovKb3u', 'ADMIN', 'admin');

insert into banking_system_db.account_holder (id, creation_date, last_update, name, password, role, username, date_of_birth, mailing_city, mailing_country, mailing_postal, mailing_street, city, country, postal_code, street_address)
values  (2, '2022-05-21 08:13:09.237024', '2022-05-21 08:13:09.237024', 'Pepito 1', '$2a$10$if.JUEa3m3enLjTxG5TuquHBUxWjzfwyvEO.GdTuNpyOHReFDH9hu', 'ACCOUNT_HOLDER', 'lil pep', '2010-10-12', 'Faketown', 'Fakeland', 'Y6712', 'Fake Street 12', 'Faketown', 'Fakeland', 'Y6712', 'Fake Street 12'),
        (3, '2022-05-21 08:13:29.223105', '2022-05-21 08:13:29.223105', 'Pepito 2', '$2a$10$jMbMCRLJRalh7u5Is/upt.2PRvBqJgS.cIjWFhQTHwmZCDulN21Cq', 'ACCOUNT_HOLDER', 'big pep', '1979-10-12', 'Faketown', 'Fakeland', 'Y6712', 'Fake Street 12', 'Faketown', 'Fakeland', 'Y6712', 'Fake Street 12');

insert into banking_system_db.third_party (id, creation_date, last_update, name, password, role, username, hashed_key, third_party_account)
values  (4, '2022-05-21 08:14:02.171945', '2022-05-21 08:14:02.171945', 'Third Party 1', '$2a$10$jDS9vtSdxxOQp4x8LTpW4.MXA85RT0Fd0joOgpVyPKdmHnPo0BaYW', 'THIRD_PARTY', 'party', 'keydementirajijiji', 1),
        (9, '2022-05-22 13:31:42.985637', '2022-05-22 13:31:42.985637', 'Third Party 2', '$2a$10$itCB8cRoVcahRrXUqR/I6.gvPshAVZzm9QU8zb9G6wmcwz.x/L2D6', 'THIRD_PARTY', 'party 2', 'keydementirajijijjii', 2);
        
insert into banking_system_db.student_checking_account (id, balance, balance_currency, creation_date, secret_key, status, primary_owner, secondary_owner)
values  (5, 33333.00, 'EUR', '2022-05-21 08:14:41.405216', '1234', 'ACTIVE', 2, 3);
 
insert into banking_system_db.checking_account (id, balance, balance_currency, creation_date, secret_key, status, primary_owner, secondary_owner, minimum_balance, minimum_balance_currency, monthly_maitenance_fee, monthly_maitenance_fee_currency)
values  (6, 8000.00, 'EUR', '2022-05-21 08:15:11.505220', '1234', 'ACTIVE', 3, 2, 250.00, 'EUR', 12.00, 'EUR');        

insert into banking_system_db.savings_account (id, balance, balance_currency, creation_date, secret_key, status, primary_owner, secondary_owner, date_interest_added, interest_rate, minimum_balance, minimum_balance_currency)
values  (7, 3200.00, 'USD', '2022-05-21 08:16:03.650583', '1234', 'ACTIVE', 2, 3, '2022-05-21', 0.1000, 301.00, 'USD');

insert into banking_system_db.credit_card_account (id, balance, balance_currency, creation_date, secret_key, status, primary_owner, secondary_owner, credit_limit, credit_limit_currency, date_interest_added, interest_rate)
values  (8, 1190.00, 'USD', '2022-05-21 08:16:30.158315', '1234', 'ACTIVE', 3, null, 200.00, 'USD', '2022-05-21', 0.1500);

insert into banking_system_db.transaction (id, amount, currency, payer_third_party_acc, secret_key, subject, target_name, target_third_party_acc, transfer_date, payer_acc_id, target_acc_id)
values  (10, 100.00, 'EUR', 1, null, 'test transaction hello!', 'Pepito 1', null, '2022-05-21 08:45:18.860627', null, 7),
        (11, 100.00, 'EUR', null, null, 'test transaction hello!', 'Third Party 1', 1, '2022-05-21 08:51:32.398929', 7, null),
        (12, 100.00, 'EUR', 1, '1234', 'test transaction hello!', 'Pepito 1', null, '2022-05-21 17:17:46.853043', null, 7),
        (13, 100.00, 'EUR', null, null, 'test transaction hello!', 'Pepito 2', null, '2022-05-22 09:56:32.144299', 7, 8),
        (14, 100.00, 'EUR', null, null, 'test transaction hello!', 'Pepito 2', null, '2022-05-21 09:18:11.358695', 8, 7),
        (15, 100.00, 'EUR', null, null, 'test transaction hello!', 'Pepito 2', null, '2022-05-21 09:18:11.358695', 8, 7);

insert into banking_system_db.hibernate_sequences (sequence_name, next_val)
values  ('default', 16);