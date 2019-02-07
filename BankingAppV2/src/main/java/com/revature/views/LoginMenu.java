package com.revature.views;

import java.util.ArrayList;
import java.util.List;

import com.revature.daos.CustomerDao;
import com.revature.models.Customer;
import com.revature.service.AccountService;
import com.revature.util.InputUtility;

//2nd menu navigated
public class LoginMenu implements View{
	
	public int inputValue;
	Customer c;
	public LoginMenu(Customer c) {
		super();
		this.c = c;
	}

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub
		System.out.println("Choose an option: ");
		System.out.println("1. Create account");
		System.out.println("2. Manage account");
		System.out.println("3. Create joint accounts");
		System.out.println("0. Back");
	}

	@Override
	public View process() {
		// TODO Auto-generated method stub
		switch(this.inputValue) {
		case 1: 
			AccountService.createAccount(c);
			break;
		case 2: 
			return new ManageMenu(c);
		case 3:
			List<Customer> customers = new ArrayList<>();
			Customer a = CustomerDao.getCustomerById("koolmo94@gmail.omc");
			Customer b = CustomerDao.getCustomerById("mo94@gmail.com");
			customers.add(a);
			customers.add(b);
			System.out.println(a);
			System.out.println(b);
			AccountService.createJointAccount(customers);
			break;
		case 0: 
			return new MainMenu();
		}
		return this;
	}

	@Override
	public void getUserInput() {
		// TODO Auto-generated method stub
		inputValue = InputUtility.getIntChoice(4);
	}

}
