package com.revature.util;

import java.util.Scanner;

public class InputUtility {
	private static Scanner scanner = new Scanner(System.in);

	public static int getIntChoice(int max) {

		int inputValue;
		// Confirm user input is int type
		do {
			

			while(!scanner.hasNextInt()) {
				scanner.nextLine();
				System.out.println("Please enter a whole number.");
			}
			//Retrieve user input
			
			inputValue = scanner.nextInt();
			scanner.nextLine();
			
			// Confirm user input is within the range of 0 - max
			if(inputValue < 0 || inputValue > max) {
				System.out.println("Please enter a number between 0 and " + max);
			}
		} while(inputValue < 0 || inputValue > max);
		
		// return user input
		return inputValue;
		
	}
	
	public static String getStringInput(int max) {
		String input;
		while(true) {
			input = scanner.nextLine();
			
			input = input.trim();
			if(input.length() == 0) {
				System.out.println("String contained no content. Please enter string again.");
				continue;
			}
			
			if(input.length() > max) {
				System.out.println("String exceeded max length of " + max + ". Please enter string again");
				continue;
			}
			
			return input;
		}
	}
	
	public static String getAccountType() {
		String accountType = "";
		boolean valid = false;
		accountType = scanner.nextLine();
		while(!valid) {
			System.out.println("Please select an account type (checkings/savings): ");
			if(accountType.equalsIgnoreCase("checkings") || accountType.equalsIgnoreCase("savings")) {
				valid = true;
			} else {
				System.out.println("Invalid selection. Please enter checkings or savings");
				break;
			}	
		}
		return accountType;
	}
}
