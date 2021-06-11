/*DROP TABLE IF EXISTS journal;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS book;*/
/*create table journal
(
	id bigint not null
		constraint transaction_pkey
			primary key,
	account varchar(255),
	amount numeric(20,2),
	destination_account varchar(255),
	is_confirmed boolean,
	reference_number varchar(6),
	transaction_date timestamp
);*/

/*CREATE TABLE employeeHealthInsurance (
  empId VARCHAR(10) NOT NULL,
  healthInsuranceSchemeName VARCHAR(100) NOT NULL,
  coverageAmount VARCHAR(100) NOT NULL
);*/

--alter table journal owner to postgres;
alter table transaction alter column transaction_date type timestamp using transaction_date::timestamp;
--INSERT INTO public.transaction (id, account, amount, destination_account, is_confirmed, reference_number, transaction_date) VALUES (2, '123', 50.00, null, true, null, E'\\xACED00057372000D6A6176612E74696D652E536572955D84BA1B2248B20C00007870770E05000007E5060A0D1B161C22260078');