package main;

import java.sql.SQLException;


import controller.MealController;
import model.Food;

public class TestCaseMain {
	public static void main(String[] args) {		
		
		//TestCase Swap And Return Calculation  
		//Tested with console instead of UI
		
		MealController mc = MealController.getInstance();
		String goal = "ENERGY (KILOCALORIES)";
		try {
			Food result = mc.swapAndReturnNewFood(0, goal);
			System.out.println(result.getDescription());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
				
				
	        
			
			
			
		
	}
}
