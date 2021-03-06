drop table if exists bank_accounts;

drop table if exists users;

create table users (
	user_name		varchar(30)		primary key,
	pass_word		varchar(30)		not null,
	first_name		varchar(30)		not null,
	last_name		varchar(30)		not null,
	is_employee		boolean			default false
);

create table bank_accounts (
	account_id		serial			primary key,
	u_user_name		varchar(30)		references users(user_name),
	account_name	varchar(30)		not null,
	balance			decimal			default 0.00,
	is_approved		boolean			default false
);



-- Create java_user and initialize privileges.
--create role java_user LOGIN password 'p4ssword';
grant all privileges on table users to java_user;
grant all privileges on table bank_accounts to java_user;
grant all privileges on sequence bank_accounts_account_id_seq to java_user;



-- Insert sample users/bank accounts into DB.
insert into users values
	('employee 1', 'i am an employee', 'bobby', 'bob', true),
	('employee 2', 'i am a better employee', 'susie', 'sue', true);

insert into users values 
	('new customer 1', 'good p4ssword', 'don', 'donathan'), 
	('new customer 2', 'good p4ssword', 'lance', 'lancelot'),
	('new customer 3', 'good p4ssword', 'ronny', 'mcron');

insert into bank_accounts(u_user_name, account_name, balance, is_approved) values
	('new customer 1', 'acc1', 100.00, true),
	('new customer 1', 'acc2', 0.00, true),
	('new customer 1', 'acc3', 50.00, true),
	('new customer 1', 'acc4', 100.00, false),
	('new customer 1', 'acc5', 0.00, false),
	('new customer 1', 'acc6', 50.00, false),
	('new customer 2', 'acc1', 100.00, false),
	('new customer 2', 'acc2', 0.00, false),
	('new customer 2', 'acc3', 50.00, false);
	