package com.revature;

import com.revature.views.MainMenu;
import com.revature.views.View;

public class Launcher {
	public static void main(String[] args) {
		
		View view = new MainMenu();
			
		do {
			view.showMenu();
			view.getUserInput();
			view = view.process();
		} while(view !=null);
	}
}
