package main;

import javax.swing.SwingUtilities;

import controller.UserController;
import view.UserFrame;

public class UserCreateMain {
public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				//User create use case
				UserFrame userView = new UserFrame();
	            UserController userController = UserController.getInstance();
	            userController.setUserFrame(userView);
	            userView.setVisible(true);
				
			}
			
			
		});
	}
}
