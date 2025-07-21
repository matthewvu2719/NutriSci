package model;

import java.util.List;

public class Meal {
	List<Food> foodList;
	
	
	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}
	
	public List<Food> getFoodList() {
		return foodList;
	}
	
}
