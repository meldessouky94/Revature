package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import com.revature.daos.AccountDao;
import com.revature.daos.CustomerDao;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.util.InputUtility;

public class AccountService {

	private static Scanner sc = new Scanner(System.in);
	private static AccountDao accountDao = new AccountDao();
//	private static CustomerDao customerDao = new CustomerDao();
	static Account account = new Account();
	static Account tempAccount = new Account();
	static int increment = 0;
	private Customer c;

	public AccountService(Customer c) {
		super();
		this.c = c;
	}

	//Creating an account and then associating it with the logged in customer for the joint table in our database
	public static void createAccount(Customer customer) {

		Account account = new Account();
		int id = (int) (Math.random() * 1000);

		account.setId(id);

		System.out.println("Are you opening a joint account?");
		account.setIsJoint(InputUtility.getStringInput(30));

		System.out.println("Are you opening a checkings or savings?");
		account.setType(InputUtility.getAccountType());

		System.out.println("Please enter initial deposit: ");
		account.setBalance(InputUtility.getIntChoice(10000));

		account = accountDao.createAccount(account, customer);
	}

	//Creating a joint account, therefore no need to ask if theyre creating a joint account
	public static void createJointAccount(List<Customer> customers) {

		Account account = new Account();
		int id = (int) (Math.random() * 1000);

		account.setId(id);

//		System.out.println("Are you opening a joint account?");
		account.setIsJoint("Joint");

		System.out.println("Are you opening a checkings or savings?");
		account.setType(InputUtility.getAccountType());

		System.out.println("Please enter initial deposit: ");
		account.setBalance(InputUtility.getIntChoice(10000));

		account = accountDao.createJointAccount(account, customers);
	}

	// basic depositing for updating the account table
	public static void deposit(Customer c) {
		List<Account> customerAccounts = new ArrayList<>();
		customerAccounts = CustomerDao.getAcounts(c.getEmail());

		Account a = null;
		while (true) {
			System.out.println("Your accounts are: ");
			for (Account acc : customerAccounts) {
				System.out.println(" " + ((Account) acc).getId());
			}
			System.out.println("Which account would you like to deposit into?");
			String input = sc.nextLine();
			int accountId = Integer.parseInt(input);
			for (int i = 0; i < customerAccounts.size(); i++) {
				if (((Account) customerAccounts.get(i)).getId() == accountId) {
					a = customerAccounts.get(i);
				}
			}
			if (a == null) {
				System.out.println("Sorry, invalid account number");
			} else {
				break;
			}
		}
		showBalance(c);
		System.out.println("Enter the amount you'd like to deposit");
		// throw numberformatexception if not found
		double amount = InputUtility.getIntChoice(1000);
		if (amount < 0) {
			System.out.println("Transaction failed, invalid deposit amount");
		} else {
			a.setBalance(a.getBalance() + amount);
			AccountDao ad = new AccountDao();
			ad.update(a);
			System.out.println("$" + amount + " deposited. Your balance is now " + a.getBalance() + ".");
		}
	}
	
	//updating balance within accounts 
	public static void withdraw(Customer c) {
		List<Account> customerAccounts = new ArrayList<>();
		customerAccounts = CustomerDao.getAcounts(c.getEmail());

		Account a = null;
		while (true) {
			System.out.println("Your accounts are: ");
			for (Account acc : customerAccounts) {
				System.out.println(" " + ((Account) acc).getId());
			}
			System.out.println("Which account would you like to withdraw from?");
			String input = sc.nextLine();
			int accountId = Integer.parseInt(input);
			for (int i = 0; i < customerAccounts.size(); i++) {
				if (((Account) customerAccounts.get(i)).getId() == accountId) {
					a = customerAccounts.get(i);
				}
			}
			if (a == null) {
				System.out.println("Sorry, invalid account number");
			} else {
				break;
			}
		}
		showBalance(c);
		System.out.println("Enter the amount you'd like to withdraw");
		// throw numberformatexception if not found
		double amount = InputUtility.getIntChoice(1000);
//		if (sc.hasNextDouble()) {
//			amount = sc.nextDouble();
//		}
		if (amount > a.getBalance() || amount < 0) {
			System.out.println("Transaction failed, invalid withdrawl amount");
		} else {
			a.setBalance(a.getBalance() - amount);
			AccountDao ad = new AccountDao();
			ad.update(a);
			System.out.println("$" + amount + " withdrawn. Your balance is now " + a.getBalance() + ".");
		}
	}

	//Return account balance
	public static void showBalance(Customer c) {
		List<Account> userAccounts = new ArrayList<>();
		userAccounts = CustomerDao.getAcounts(c.getEmail());

		for (Account a : userAccounts) {
			System.out.println("Account no. " + a.getId() + ": $" + a.getBalance());
		}
	}

	//Performing both Deposit and Withdraw and committing it at the same time
	public static void transfer(Customer c) {
		List<Account> sourceAccounts = new ArrayList<>();
		sourceAccounts = CustomerDao.getAcounts(c.getEmail());

		Account a = null;
		while (true) {
			System.out.println("Your accounts are: ");
			for (Account acc : sourceAccounts) {
				System.out.println(" " + ((Account) acc).getId());
			}
			System.out.println("Which account would you like to transfer from?");
			String input = sc.nextLine();
			int accountId = Integer.parseInt(input);
			for (int i = 0; i < sourceAccounts.size(); i++) {
				if (((Account) sourceAccounts.get(i)).getId() == accountId) {
					a = sourceAccounts.get(i);
				}
			}
			if (a == null) {
				System.out.println("Sorry, invalid account number");
			} else {
				break;
			}
		}
		showBalance(c);
		System.out.println("Enter the amount you'd like to transfer");
		// throw numberformatexception if not found
		double amount = InputUtility.getIntChoice(1000);
		if (amount > a.getBalance() || amount < 0) {
			System.out.println("Transaction failed, invalid withdrawl amount");
		}
		a.setBalance(a.getBalance() - amount);

		Account b = null;
		while (true) {
			System.out.println("Your accounts are: ");
			for (Account acc : sourceAccounts) {
				System.out.println(" " + ((Account) acc).getId());
			}
			System.out.println("Which account would you like to transfer to?");
			String input = sc.nextLine();
			int accountId = Integer.parseInt(input);
			for (int i = 0; i < sourceAccounts.size(); i++) {
				if (((Account) sourceAccounts.get(i)).getId() == accountId) {
					b = sourceAccounts.get(i);
				}
			}
			if (b == null) {
				System.out.println("Sorry, invalid account number");
			} else {
				break;
			}
		}
		showBalance(c);
		b.setBalance(b.getBalance() + amount);
		AccountDao ad = new AccountDao();
		ad.update(a);
		ad.update(b);
		System.out.println("$" + amount + " deposited. Your balance is now " + b.getBalance() + ".");
	}
}
