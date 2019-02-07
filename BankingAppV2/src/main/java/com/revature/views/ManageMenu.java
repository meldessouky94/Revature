package com.revature.views;

import com.revature.models.Customer;
import com.revature.service.AccountService;
import com.revature.util.InputUtility;

public class ManageMenu implements View{
	
	Customer c;
	
	public int inputValue;
	public ManageMenu(Customer c) {
		super();
		this.c = c;
	}

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub
		System.out.println("Choose an option: ");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Transfer");
		System.out.println("4. Show Accounts");
		System.out.println("0. Back");
	}

	@Override
	public View process() {
		// TODO Auto-generated method stub
		switch(this.inputValue) {
		case 1: 
			AccountService.deposit(c);
			break;
		case 2:
			AccountService.withdraw(c);
			break;
		case 3:
			AccountService.transfer(c);
			break;
		case 0: return new LoginMenu(c);
		}
		return this;
	}

	@Override
	public void getUserInput() {
		// TODO Auto-generated method stub
		inputValue = InputUtility.getIntChoice(4);
	}

}
