package com.revature.views;

/**
 * Should be an abstract presentation of a view layer
 * that implements some basic functionality connecting 
 * user input and presentation to a functional service
 */
public interface View {
	/**
	 * Display a menu of options to the user
	 * Every view should have this option so t hat we can present them 
	 * with the landing page for that particular View
	 */
	void showMenu();
	
	/**
	 * Hooks user choice into a functional service process
	 * @return
	 */
	View process();
	
	/**
	 * Standard method for getting user input regarding the
	 * menu shown from {@Link #showMenu()}
	 */
	void getUserInput();
}