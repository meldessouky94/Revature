CREATE table customers(
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(30) PRIMARY KEY,
	c_password VARCHAR(30)
)

DROP TABLE customers
DROP TABLE customersAccounts
DROP TABLE accounts

CREATE table accounts(
	id SERIAL PRIMARY KEY,
	balance NUMERIC,
	a_type VARCHAR(30),
	isJoint VARCHAR(30)
)

CREATE table customersAccounts(
	aid integer REFERENCES accounts(id),
	cid VARCHAR(30) REFERENCES customers(email)
)
