package com.revature.service;

import java.util.List;
import java.util.Scanner;

import com.revature.daos.AccountDao;
import com.revature.daos.CustomerDao;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.util.InputUtility;

public class AccountService {
	
	private static Scanner scanner = new Scanner(System.in);
	private static AccountDao accountDao = new AccountDao();
	private static CustomerDao customerDao = new CustomerDao();
	static Account account = new Account();
	static Account tempAccount = new Account();

	public static void createAccount() {
		
		Account account = new Account();
		System.out.println("Are you opening a joint account?");
		account.setIsJoint(InputUtility.getStringInput(30));
		
		System.out.println("Are you opening a checkings or savings?");
		account.setType(InputUtility.getAccountType());
		
		System.out.println("Please enter initial deposit: ");
		account.setBalance(InputUtility.getIntChoice(30));
		
		account = accountDao.insertAccount(account);
		int id = accountDao.getNextAccountID();
		Customer customer = new Customer();
		String customerEmail  = CustomerService.customer.getEmail();
		Account a = new Account(id, account.getBalance(), account.getType(), account.getIsJoint());
		account = accountDao.createAccount(account, customer);
	}	
	
	public static void showAccounts() {
		String customerEmail  = CustomerService.customer.getEmail();
		List<Account> accounts = accountDao.getAccounts();
		if(accounts == null || accounts.size() == 0) {
			System.out.println("There are no accounts to show");
		} else {
			for(final Account account : accounts) {
				System.out.println("Account ID: " + account.getid() + "Account type: " + account.getType() + "Joint account: " 
						+ account.getIsJoint() + "Account balance: $" + account.getBalance());
			}
		}
	}
	
	public static void deposit() {
		System.out.println("How much would you like to deposit?");
		double amount = scanner.nextDouble();
		System.out.println("Which account");
		showAccounts();
	}
	
//	public static void deposit() {
//		double amount = 0;
//		System.out.println("Please enter amount");
//		String input = scanner.nextLine();
//		amount = Double.parseDouble(scanner.nextLine());
//		if(amount < 0) {
//			System.out.println("Invalid input\n");
//			new ManageMenu();
//		}
//		if(amount > 0) {
//			if(amount > a.getBalance()) {
//				a.setBalance(a.getBalance() - amount);
//			}
//		}
//		
//	}
	
//	public static void deposit(Customer c, Scanner sc) {
//		List<Account> customerAccounts = new ArrayList<>();
//		CustomerDao cd = new CustomerDao();
//		customerAccounts = cd.getAcounts(c.getEmail());
//		
//		Account a = null;
//		while(true) {
//			System.out.println("Your accounts are: ");
//			for (Account acc: customerAccounts) {
//				System.out.println(" " + acc.getid());
//			}
//			System.out.println("Which account would you like to deposit into?");
//			String input = sc.nextLine();
//			int accountId = Integer.parseInt(input);
//			for(int i = 0; i < customerAccounts.size(); i++) {
//				if(customerAccounts.get(i).getid()==accountId) {
//					a.customerAccounts.get(i);
//				}
//			}
//			if(a==null) {
//				""
//			}
//		}
//	}
}
