package com.revature.service;

import java.util.Scanner;

import com.revature.daos.CustomerDao;
import com.revature.models.Customer;
import com.revature.util.InputUtility;
import com.revature.views.MainMenu;
import com.revature.views.ManageMenu;
import com.revature.views.View;

public class CustomerService {

	private static Scanner scanner = new Scanner(System.in);
	static CustomerDao customerDao = new CustomerDao();
	public static Customer customer = new Customer();
	static Customer customerTemp = new Customer();

	public static void CreateCustomer() {

		Customer customer = new Customer();
		System.out.println("Please enter your first name: ");
		customer.setFirstName(InputUtility.getStringInput(30));

		System.out.println("Please enter your last name: ");
		customer.setLastName(InputUtility.getStringInput(30));

		System.out.println("Please enter your email: ");
		customer.setEmail(InputUtility.getStringInput(30));

		System.out.println("Please create a password: ");
		customer.setPassword(InputUtility.getStringInput(30));

//		CustomerDao customerDao = new CustomerDao();
		if(customerDao.getCustomerById(customer.getEmail()) == null) {
		customerDao.insertCustomer(customer);
		} else {
			System.out.println("Customer already exists!");
		}	
	}
		
	public static void login() {
		boolean loggedIn = false;
		customer = null;
		while(!loggedIn) {
				System.out.println("Enter email: ");
				String email = scanner.nextLine();
				System.out.println("Enter password");
				String pass = scanner.nextLine();
				if(authenticate(email, pass)) {
					customer = customerDao.getCustomerById(email);
					System.out.println("Login success!");
					loggedIn = true;
				} else {
				System.out.println("Incorrect email or password");
				}
		}
	}
	
	public static boolean authenticate(String email, String password) {
		Customer c = customerDao.getCustomerById(email);
		if(c == null) {
			return false;
		} else {
			if(c.getPassword().equals(password)) {
			return true;
			}
		return false;
		}
	}
}
//	public static void login() {
//		System.out.println("Enter email: ");
//		String email = scanner.nextLine();
//		System.out.println("Enter password: ");
//		String password = scanner.nextLine();
//		
//		Customer c = new Customer();
//		CustomerDao cd = new CustomerDao();
//		
//		c.setLoggedIn(false);
//
//		if (cd.getCustomerById(email) != null) {
//			c = cd.getCustomerById(email);
//			if (c.getPassword().contentEquals(password)) {
//				System.out.println("Success!");
//				customerTemp = c;
//				c.setLoggedIn(true);
//			} else {
//				System.out.println("Incorrect email or password");
//				c.setLoggedIn(false);
//			}
//		} if(cd.getCustomerById(email) == null) {
//			System.out.println("Customer does not exist");
//			c.setLoggedIn(false);
//		}
//	}

//	public static void login() {
//		List<Account> customerAccounts = new ArrayList<>();
//		boolean badInput = true;
//		while (badInput) {
//			System.out.println("Enter your email");
//			Scanner sc = new Scanner(System.in);
//			String input = sc.nextLine();
//			input = input.trim();
//			if (customerDao.getCustomerById(input) == null) {
//				System.out.println("No such Customer exists");
//			} else {
//				customer = customerDao.getCustomerById(input);
//				if(customer.getPassword().equals(password)) {
//					customerTemp = c;
//					
//				}
//				
//				break;
//			}
//			sc.close();
//		}
//	}

//	public static void passLogin() {
//		boolean badInput = true;
//		while(badInput) {
//			Scanner sc2 = new Scanner(System.in);
//			String input2 = sc2.nextLine();
//			System.out.println("Enter your password");
//			if (input2.equals(customer.getPassword())) {
//				badInput = false;
//				customer.setLoggedIn(true);
//				System.out.println("You've just been logged in.");
//				// go to post-login page
//			} else if (input2.equals("b")) {
//				badInput = false;
//				new MainMenu();
//			} else {
//				System.out.println("Incorrect password. Please try again");
//			}
//			sc2.close();
//		}
//	}
