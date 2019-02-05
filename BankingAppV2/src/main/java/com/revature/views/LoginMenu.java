package com.revature.views;

import com.revature.service.AccountService;
import com.revature.util.InputUtility;

public class LoginMenu implements View{

	public int inputValue;

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub
		System.out.println("Choose an option: ");
		System.out.println("1. Create account");
		System.out.println("2. Manage account");
		System.out.println("0. Back");
	}

	@Override
	public View process() {
		// TODO Auto-generated method stub
		switch(this.inputValue) {
		case 1: AccountService.createAccount();return this; 
		case 2: return new ManageMenu();
		case 0: return new MainMenu();
		}
		return this;
	}

	@Override
	public void getUserInput() {
		// TODO Auto-generated method stub
		inputValue = InputUtility.getIntChoice(3);
	}

}
