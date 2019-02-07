package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.daos.AccountDao;
import com.revature.daos.CustomerDao;
import com.revature.models.Customer;
import com.revature.util.InputUtility;

public class CustomerService {

	private static Scanner scanner = new Scanner(System.in);
	public static CustomerDao customerDao = new CustomerDao();
	public static Customer customer = new Customer();
	static Customer customerTemp = new Customer();
	static AccountDao accountDao = new AccountDao();

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
		if (customerDao.getCustomerById(customer.getEmail()) == null) {
			customerDao.insertCustomer(customer);
		} else {
			System.out.println("Customer already exists!");
		}
	}

	public static void CreateJointCustomers() {
		List<Customer> customers = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			Customer customer = new Customer();
			System.out.println("Please enter your first name: ");
			customer.setFirstName(InputUtility.getStringInput(30));

			System.out.println("Please enter your last name: ");
			customer.setLastName(InputUtility.getStringInput(30));

			System.out.println("Please enter your email: ");
			customer.setEmail(InputUtility.getStringInput(30));

			System.out.println("Please create a password: ");
			customer.setPassword(InputUtility.getStringInput(30));

			if (customerDao.getCustomerById(customer.getEmail()) == null) {
				customerDao.insertCustomer(customer);
				customers.add(customer);
			} else {
				System.out.println("Customer already exists!");
			}
		}
		AccountService.createJointAccount(customers);

	}

	// User input for login
	public static Customer login() {

		customer = null;
		for (int i = 0; i < 3; i++) {
			System.out.println("Enter email: ");
			String email = scanner.nextLine();
			System.out.println("Enter password");
			String pass = scanner.nextLine();
			if (authenticate(email, pass)) {
				customer = customerDao.getCustomerById(email);
				System.out.println("Login success!");
				return customer;
			} else {
				System.out.println("Incorrect email or password");
			}
		}
		return null;
	}

	// To  make sure password matches
	public static boolean authenticate(String email, String password) {
		Customer c = customerDao.getCustomerById(email);
		if (c == null) {
			return false;
		} else {
			if (c.getPassword().equals(password)) {
				return true;
			}
			return false;
		}
	}
}
