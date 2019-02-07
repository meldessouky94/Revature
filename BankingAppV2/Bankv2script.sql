CREATE table customers(
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(30) PRIMARY KEY,
	c_password VARCHAR(30)
)

DROP TABLE customers;
DROP TABLE customersAccounts;
DROP TABLE accounts;

CREATE table accounts(
	id INT PRIMARY KEY,
	balance NUMERIC(12,2),
	a_type VARCHAR(30),
	isJoint VARCHAR(30)
)

CREATE table customersaccounts(
	id INT REFERENCES accounts(id) NOT NULL,
	email VARCHAR(30) REFERENCES customers(email) NOT NULL
)