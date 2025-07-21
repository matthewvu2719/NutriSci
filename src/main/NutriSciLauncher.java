package main;

import javax.swing.SwingUtilities;

import controller.MealController;
import controller.UserController;
import view.MealFrame;
import view.UserFrame;

public class NutriSciLauncher {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				UserFrame userView = new UserFrame();
	            UserController userController = UserController.getInstance();
	            userController.setUserFrame(userView);
	            userView.setVisible(true);
				

				MealFrame mealFrame = new MealFrame();
				MealController mealController = MealController.getInstance();
				mealController.setMealFrame(mealFrame);
				mealFrame.setVisible(true);
			}
			
		});
	}

}
