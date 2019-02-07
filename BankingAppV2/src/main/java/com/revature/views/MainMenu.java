package com.revature.views;

import com.revature.models.Customer;
import com.revature.service.CustomerService;
import com.revature.util.InputUtility;

public class MainMenu implements View{
	
	public int inputValue;

	public void showMenu() {
		System.out.println("Welcome to the bank");
		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. Joint Login");
		System.out.println("0. Quit");
	}

	public View process() {
		switch(inputValue) {
		case 0: 
			return null;
		case 1:
			CustomerService.CreateCustomer(); 
			return new MainMenu();
		case 2: 
			Customer c = CustomerService.login();
			if(c==null) {
				break;
			}
			return new LoginMenu(c);
		case 3:
			CustomerService.CreateJointCustomers();
			return new MainMenu();
		}
		return null;
	}

	public void getUserInput() {
		this.inputValue = InputUtility.getIntChoice(3);
	}

}
