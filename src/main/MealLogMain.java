package main;

import javax.swing.SwingUtilities;

import controller.MealController;
import view.MealFrame;


public class MealLogMain {
public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
	            //Meal log use case
				MealFrame mealFrame = new MealFrame();
				MealController mealController = MealController.getInstance();
				mealController.setMealFrame(mealFrame);
				mealFrame.setVisible(true);
			}
			
			
			
		});
	}
}
